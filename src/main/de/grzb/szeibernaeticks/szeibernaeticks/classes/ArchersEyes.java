package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.SzeibernaetickItem;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.Szeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickCapabilityProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyEvent.Demand;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyUser;
import main.de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityArrowFake;
import main.de.grzb.szeibernaeticks.szeibernaeticks.handler.ArchersEyesHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;

@Szeibernaetick(handler = { ArchersEyesHandler.class, }, item = ArchersEyes.Item.class)
public class ArchersEyes extends EnergyUserBase implements ISzeibernaetick, IEnergyUser {
    @Szeibernaetick.Identifier
    public static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "ArchEyes");
    @Szeibernaetick.ItemInject
    public static final SzeibernaetickItem item = null;

    private static final BodyPart bodyPart = BodyPart.EYES;
    private int consumption = 1;
    private int ticksRemaining = 0;
    private boolean running = true;

    @Override
    public Iterable<Switch> getSwitches() {
        ArrayList<Switch> list = new ArrayList<Switch>();
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
        tag.setBoolean("running", running);
        return tag;
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        storage = nbt.getInteger("storage");
        if(nbt.getInteger("maxStorage") > 0) {
            maxStorage = nbt.getInteger("maxStorage");
        }
        ticksRemaining = nbt.getInteger("ticksRemaining");
        running = nbt.getBoolean("running");
    }

    @Override
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public void grantVision(Tick e) {
        if(running) {
            Log.log("[ArchEyesCap] ArchEyes attempting to grant vision!", LogType.DEBUG, LogType.SZEIBER_CAP,
                    LogType.SPAMMY);

            Entity shooter = e.getEntity();

            // Grant vision if necessary
            if(ticksRemaining <= 0) {
                Log.log("[ArchEyesCap] ArchEyes granting Vision!", LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SPAMMY);
                Demand demand = new Demand(shooter, consumption);
                MinecraftForge.EVENT_BUS.post(demand);
                if(demand.isMet()) {
                    ticksRemaining += 20;
                }
            }

            // Grant vision if it is activated
            if(ticksRemaining > 0) {
                --ticksRemaining;

                if(shooter.world.isRemote) {
                    Log.log("[ArchEyesCap] Attempting to spawn fake arrow!.", LogType.DEBUG, LogType.SZEIBER_CAP,
                            LogType.SPAMMY);
                    ItemStack itemstack = e.getItem();
                    ItemBow bow = (ItemBow) itemstack.getItem();

                    // Calculate the current force of the bow
                    int charge = bow.getMaxItemUseDuration(itemstack) - e.getDuration();
                    float force = charge / 20.0F;
                    force = (force * force + force * 2.0F) / 3.0F;

                    if(force > 1.0F) {
                        force = 1.0F;
                    }

                    EntityArrowFake entityarrow = new EntityArrowFake(e.getEntity().world, e.getEntityLiving());
                    entityarrow.shoot(shooter, shooter.rotationPitch, shooter.rotationYaw, 0.0F, force * 3.0F, 1.0F);
                    e.getEntity().world.spawnEntity(entityarrow);
                }
            }
        }
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
            ArchersEyes cap = new ArchersEyes();
            if(nbt != null) {
                cap.fromNBT(nbt);
            }
            return new SzeibernaetickCapabilityProvider(cap);
        }
    }
}
