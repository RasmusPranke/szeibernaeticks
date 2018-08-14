package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyConsumptionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public class RunnersLegs extends SzeibernaetickBase implements IEnergyConsumer {
    private static final String identifier = Szeibernaeticks.MOD_ID + ":RunnersLegs";
    private int maxStorage = 20;
    private int storage = 0;
    private int consumption = 5;
    private boolean running = true;

    private void SwitchActive() {
        running = !running;
    }

    private Switch onOff = new Switch.BooleanSwitch(this::SwitchActive, identifier + ":OnOff");

    @Override
    public Iterable<Switch> GetSwitches() {
        ArrayList<Switch> list = new ArrayList<Switch>();
        list.add(onOff);
        return list;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("storage", this.storage);
        tag.setInteger("maxStorage", this.maxStorage);
        tag.setBoolean("running", running);
        return tag;
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        this.storage = nbt.getInteger("storage");
        if(nbt.getInteger("maxStorage") > 0) {
            this.maxStorage = nbt.getInteger("maxStorage");
        }
        this.running = nbt.getBoolean("running");
    }

    @Override
    public BodyPart getBodyPart() {
        return BodyPart.LEGS;
    }

    public boolean grantSpeed(Entity target) {
        boolean granted = false;

        if(this.storage >= this.consumption) {
            this.storage -= this.consumption;
            granted = true;
        }

        if(this.storage < this.consumption) {
            int missingEnergy = this.maxStorage - this.storage;
            EnergyConsumptionEvent event = new EnergyConsumptionEvent(target, missingEnergy);
            MinecraftForge.EVENT_BUS.post(event);
            this.storage += (missingEnergy - event.getRemainingAmount());
        }

        return granted;
    }

    @Override
    public EnergyPriority currentConsumptionPrio() {
        return EnergyPriority.FILL_ASAP;
    }

    @Override
    public boolean canStillConsume() {
        return this.storage < this.maxStorage;
    }

    @Override
    public int consume() {
        if(this.canStillConsume()) {
            this.storage++;
            return 1;
        }
        return 0;
    }

    @Override
    public int getMaxEnergy() {
        return maxStorage;
    }

    @Override
    public int getCurrentEnergy() {
        return storage;
    }

    @Override
    public int store(int amountToStore) {
        int consumed = 0;

        while(consume() != 0) {
            consumed++;
        }

        return consumed;
    }

    @Override
    public int retrieve(int amountToRetrieve) {

        return 0;
    }

}
