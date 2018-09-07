package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.SzeibernaetickItem;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeiberClass;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickCapabilityProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyEvent.Demand;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyUser;
import main.de.grzb.szeibernaeticks.szeibernaeticks.handler.RadarEyesHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

@SzeiberClass(handler = { RadarEyesHandler.class }, item = RadarEyes.Item.class)
public class RadarEyes extends EnergyUserBase implements ISzeibernaetick, IEnergyUser {
    @SzeiberClass.Identifier
    public static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "RadEyes");
    @SzeiberClass.ItemInject
    public static final Item item = null;
    private static final BodyPart bodyPart = BodyPart.EYES;
    private int consumption = 1;
    private int ticksRemaining = 0;
    private boolean active = false;

    private boolean isRunning() {
        return onOff.getValue();
    }

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

    {
        Log.log("Creating instance of " + this.getClass(), LogType.SZEIBER_CAP, LogType.DEBUG, LogType.INSTANTIATION);
    }

    @Override
    public SzeibernaetickIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("storage", storage);
        tag.setInteger("maxStorage", maxStorage);
        tag.setInteger("ticksRemaining", ticksRemaining);
        return tag;
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        storage = nbt.getInteger("storage");
        if(nbt.getInteger("maxStorage") > 0) {
            maxStorage = nbt.getInteger("maxStorage");
        }
        ticksRemaining = nbt.getInteger("ticksRemaining");
    }

    @Override
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public void grantVision(LivingUpdateEvent e) {
        if(isRunning()) {
            Entity shooter = e.getEntity();

            // Grant vision if necessary
            if(ticksRemaining <= 0) {
                Demand demand = new Demand(shooter, consumption);
                MinecraftForge.EVENT_BUS.post(demand);
                if(demand.isMet()) {
                    Log.log("[RadEyesCap] ArchEyes granting Vision!", LogType.DEBUG, LogType.SZEIBER_CAP,
                            LogType.SPAMMY);
                    ticksRemaining += 20;
                }
            }

            // Check whether vision should still be granted
            if(ticksRemaining > 0) {
                --ticksRemaining;
                active = true;
            }
            else {
                active = false;
            }

            // Set Vision accordingly
            // TODO: This is mega inefficient
            if(shooter.world.isRemote) {
                for(Entity oneOfAll : shooter.getEntityWorld().getLoadedEntityList()) {
                    if(oneOfAll instanceof EntityLivingBase) {
                        ((EntityLivingBase) oneOfAll).setGlowing(active);
                    }
                }
            }
        }
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
            RadarEyes cap = new RadarEyes();
            if(nbt != null) {
                cap.fromNBT(nbt);
            }
            return new SzeibernaetickCapabilityProvider(cap);
        }
    }

    @Override
    public String toNiceString() {
        return "Radar Eyes";
    }
}
