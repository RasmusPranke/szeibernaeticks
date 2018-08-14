package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyConsumptionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public class SyntheticEyes extends SzeibernaetickBase implements IEnergyConsumer {
    private static final String identifier = Szeibernaeticks.MOD_ID + ":SynthEyes";
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

    {
        Log.log("Creating instance of " + this.getClass(), LogType.SZEIBER_CAP, LogType.DEBUG, LogType.INSTANTIATION);
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
        return BodyPart.EYES;
    }

    public boolean grantVision(Entity target) {
        Log.log("[SynthEyesCap] SynthEyes attempting to grant vision!", LogType.DEBUG, LogType.SZEIBER_CAP,
                LogType.SPAMMY);
        boolean granted = false;

        if(this.storage >= this.consumption) {
            Log.log("[SynthEyesCap] SynthEyes granting Vision!", LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SPAMMY);
            this.storage -= this.consumption;
            granted = true;
        }

        if(this.storage < this.consumption) {
            Log.log("[SynthEyesCap] SynthEyes missing Energy, posting Event.", LogType.DEBUG, LogType.SZEIBER_CAP,
                    LogType.SPAMMY);
            int missingEnergy = this.maxStorage - this.storage;
            EnergyConsumptionEvent event = new EnergyConsumptionEvent(target, missingEnergy);
            MinecraftForge.EVENT_BUS.post(event);
            Log.log("[SynthEyesCap] Event granted " + (missingEnergy - event.getRemainingAmount()) + " Energy.",
                    LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SPAMMY);
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
        Log.log("[SynthEyesCap] SynthEyes attempting to consume energy!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP, LogType.SPAMMY);
        if(this.canStillConsume()) {
            this.storage++;
            Log.log("[SynthEyesCap] SynthEyes consuming energy! Now storing: " + this.storage, LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP, LogType.SPAMMY);
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
