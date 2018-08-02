package main.de.grzb.szeibernaeticks.szeibernaeticks.capability;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyProductionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyProducer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.feedback.EnergyFeedbackDamage;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public class DynamoJoints implements ISzeibernaetick, IEnergyConsumer, IEnergyProducer {

    private int maxStorage;
    private int storage;
    private float fractionalStorage;

    @Override
    public String getIdentifier() {
        return "dynamoJoints";
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
        return BodyPart.JOINTS;
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

        if(production.getRemainingAmount() > 0) {
            entity.attackEntityFrom(new EnergyFeedbackDamage(this), production.getRemainingAmount());
        }
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

}
