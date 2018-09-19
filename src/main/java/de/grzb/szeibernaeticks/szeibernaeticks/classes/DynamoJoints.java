package de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import de.grzb.szeibernaeticks.Szeibernaeticks;
import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.item.SzeibernaetickItem;
import de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeiberClass;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickCapabilityProvider;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyEvent.Supply;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyUser;
import de.grzb.szeibernaeticks.szeibernaeticks.handler.DynamoJointsHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

@SzeiberClass(handler = { DynamoJointsHandler.class }, item = DynamoJoints.Item.class)
public class DynamoJoints extends EnergyUserBase implements ISzeibernaetick, IEnergyUser {
    @SzeiberClass.Identifier
    public static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "DynamoJoints");
    @SzeiberClass.ItemInject
    public static final Item item = null;
    private static final BodyPart bodyPart = BodyPart.JOINTS;

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
    }

    @Override
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    /**
     * Produces energy based on the given fall height.
     *
     * @param height
     *            How far this fell.
     * @param entity
     *            The entity this happened on
     * @return The amount of energy produced.
     */
    public void produce(float height, Entity entity) {
        this.fractionalStorage += height / 4;
        int energyProduced = 0;
        if(this.fractionalStorage > 0) {
            energyProduced = (int) this.fractionalStorage;

            Log.log("[DynJointsCap] Producing energy: " + energyProduced, LogType.DEBUG, LogType.SZEIBER_ENERGY,
                    LogType.SZEIBER_CAP);
            Supply supply = new Supply(entity, energyProduced);
            MinecraftForge.EVENT_BUS.post(supply);

            this.fractionalStorage = this.fractionalStorage - energyProduced;
        }
    }

    @Override
    public Iterable<Switch> getSwitches() {
        return new ArrayList<Switch>();
    }

    @Override
    public ItemStack generateItemStack() {
        ItemStack stack = new ItemStack(item);
        return stack;
    }

    public static class Item extends SzeibernaetickItem {
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

    @Override
    public String toNiceString() {
        return "Dynamo Joints";
    }
}
