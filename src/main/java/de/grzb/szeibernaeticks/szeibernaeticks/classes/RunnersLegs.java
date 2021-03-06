package de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import de.grzb.szeibernaeticks.Szeibernaeticks;
import de.grzb.szeibernaeticks.item.SzeibernaetickItem;
import de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeiberClass;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickCapabilityProvider;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyEvent.Demand;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyUser;
import de.grzb.szeibernaeticks.szeibernaeticks.handler.RunnersLegsHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

@SzeiberClass(handler = { RunnersLegsHandler.class }, item = RunnersLegs.Item.class)
public class RunnersLegs extends EnergyUserBase implements ISzeibernaetick, IEnergyUser {
    @SzeiberClass.Identifier
    public static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "RunnersLegs");
    @SzeiberClass.ItemInject
    public static final Item item = null;
    private static final BodyPart bodyPart = BodyPart.LEGS;
    private int maxStorage = 20;
    private int storage = 0;
    private int consumption = 5;

    private boolean running() {
        return onOff.getValue();
    };

    private class OnOffSwitch extends Switch.BooleanSwitch {
        public OnOffSwitch(ISzeibernaetick sourceSzeiber, String name) {
            super(sourceSzeiber, name);
        }

        @Override
        public boolean isActive() {
            return true;
        }

    }

    private OnOffSwitch onOff = new OnOffSwitch(this, "OnOff");

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
        // TODO: Make sure all states are saved in NBT in every szeibernaetick
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("storage", this.storage);
        tag.setInteger("maxStorage", this.maxStorage);
        return tag;
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        this.storage = nbt.getInteger("storage");
        if(nbt.getInteger("maxStorage") > 0) {
            this.maxStorage = nbt.getInteger("maxStorage");
        }
    }

    @Override
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public boolean grantSpeed(Entity target) {
        boolean granted = false;
        Demand demand = new Demand(target, consumption);
        MinecraftForge.EVENT_BUS.post(demand);
        if(demand.isMet()) {
            granted = true;
        }
        return granted;
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
            RunnersLegs cap = new RunnersLegs();
            if(nbt != null) {
                cap.fromNBT(nbt);
            }
            return new SzeibernaetickCapabilityProvider(cap);
        }
    }

    @Override
    public String toNiceString() {
        return "Runners Legs";
    }
}
