package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.item.ModItems;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickCapabilityProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyConsumptionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.RunnersLegsHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class RunnersLegs implements ISzeibernaetick, IEnergyConsumer {
    private static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "RunnersLegs");
    private static final BodyPart bodyPart = BodyPart.LEGS;
    private int maxStorage = 20;
    private int storage = 0;
    private int consumption = 5;
    private boolean running = true;

    private class OnOffSwitch extends Switch.BooleanSwitch {
        public OnOffSwitch(ISzeibernaetick sourceSzeiber, String name) {
            super(sourceSzeiber, name);
        }

        @Override
        protected boolean getValue() {
            return running;
        }

        @Override
        protected void setValue(boolean val) {
            running = val;
        }

        @Override
        public boolean IsActive() {
            return true;
        }

    }

    private Switch onOff = new OnOffSwitch(this, "OnOff");

    @Override
    public Iterable<Switch> getSwitches() {
        ArrayList<Switch> list = new ArrayList<Switch>();
        list.add(onOff);
        return list;
    }

    @Override
    public SzeibernaetickIdentifier getIdentifier() {
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
        return bodyPart;
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

    @Override
    public ItemStack generateItemStack() {
        ItemStack stack = new ItemStack(Item.item);
        return stack;
    }

    public static class Item extends SzeibernaetickBase {
        public static Item item;

        public Item() {
            super(identifier);
        }

        @Override
        public BodyPart getBodyPart() {
            return bodyPart;
        }

        @Override
        public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
            RunnersLegs cap = new RunnersLegs();
            if(nbt != null) {
                cap.fromNBT(nbt);
            }
            return new SzeibernaetickCapabilityProvider(cap);
        }
    }

    public static void register(ModItems.RegisteringMethod method) {
        Item.item = method.registerSzeibernaetick(new Item(), RunnersLegsHandler.class);
    }
}
