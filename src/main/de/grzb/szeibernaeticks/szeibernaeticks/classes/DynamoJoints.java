package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.ModItems;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickCapabilityProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickMapper;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyProductionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyProducer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.handler.DynamoJointsHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class DynamoJoints implements ISzeibernaetick, IEnergyConsumer, IEnergyProducer {
    private static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "DynamoJoints");
    private static final BodyPart bodyPart = BodyPart.JOINTS;

    private int maxStorage = 100;
    private int storage = 0;
    private float fractionalStorage = 0;

    @Override
    public SzeibernaetickIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("maxStorage", this.maxStorage);
        tag.setInteger("storage", this.storage);
        tag.setFloat("fractionalStorage", this.fractionalStorage);
        return tag;
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        this.maxStorage = nbt.getInteger("maxStorage");
        this.storage = nbt.getInteger("storage");
        this.fractionalStorage = nbt.getFloat("fractionalStorage");

        if(this.maxStorage == 0) {
            this.maxStorage = 100;
        }

        return;
    }

    @Override
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    // IEnergyConsumer Implementation

    @Override
    public EnergyPriority currentConsumptionPrio() {
        return EnergyPriority.EMPTY_FAST;
    }

    @Override
    public boolean canStillConsume() {
        return this.storage < this.maxStorage;
    }

    @Override
    public int consume() {
        Log.log("[DynJointsCap] DynJoints attempting to consume energy!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP, LogType.SPAMMY);
        if(this.canStillConsume()) {
            this.storage++;
            Log.log("[DynJointsCap] DynJoints consuming energy! Now storing: " + this.storage, LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP, LogType.SPAMMY);
            return 1;
        }
        return 0;
    }

    // IEnergyProducer Implementation

    @Override
    public EnergyPriority currentProductionPriority() {
        return EnergyPriority.EMPTY_FAST;
    }

    @Override
    public boolean canStillProduce() {
        return this.storage > 0;
    }

    @Override
    public int produceAdHoc() {
        Log.log("[DynJointsCap] DynJoints attempting to produce energy!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP, LogType.SPAMMY);
        if(this.canStillProduce()) {
            this.storage--;
            Log.log("[DynJointsCap] DynJoints produced energy! Remaining storage: " + this.storage, LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP, LogType.SPAMMY);
            return 1;
        }
        return 0;
    }

    /**
     * Produces energy based on the given fall height.
     *
     * @param height
     *            How far this fell.
     * @param entity
     *            The entity this happend on
     * @return The amount of energy produced.
     */
    public int produce(float height, Entity entity) {
        this.fractionalStorage += height / 4;
        int energyProduced = 0;
        if(this.fractionalStorage > 0) {
            energyProduced = (int) this.fractionalStorage;
            this.fractionalStorage = this.fractionalStorage - energyProduced;
        }
        Log.log("[DynJointsCap] Producing energy: " + energyProduced, LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP);
        EnergyProductionEvent production = new EnergyProductionEvent(entity, energyProduced);
        MinecraftForge.EVENT_BUS.post(production);

        return energyProduced;
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
    public Iterable<Switch> getSwitches() {
        return new ArrayList<Switch>();
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
            DynamoJoints cap = new DynamoJoints();
            if(nbt != null) {
                cap.fromNBT(nbt);
            }
            return new SzeibernaetickCapabilityProvider(cap);
        }
    }

    public static void register(ModItems.RegisteringMethod method) {
        Item.item = method.registerSzeibernaetick(new Item(), DynamoJointsHandler.class);
        SzeibernaetickMapper.INSTANCE.register(DynamoJoints.class, Item.item, identifier);
    }

}
