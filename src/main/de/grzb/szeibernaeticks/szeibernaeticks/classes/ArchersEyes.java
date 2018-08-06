package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyConsumptionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityArrowFake;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;

public class ArchersEyes implements ISzeibernaetick, IEnergyConsumer {
    private int maxStorage = 20;
    private int storage = 0;
    private int consumption = 1;
    private int ticksRemaining = 0;

    {
        Log.log("Creating instance of " + this.getClass(), LogType.SZEIBER_CAP, LogType.DEBUG, LogType.INSTANTIATION);
    }

    @Override
    public String getIdentifier() {
        return "ArchEyes";
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
        return BodyPart.EYES;
    }

    public void grantVision(Tick e) {
        Log.log("[ArchEyesCap] ArchEyes attempting to grant vision!", LogType.DEBUG, LogType.SZEIBER_CAP,
                LogType.SPAMMY);

        Entity shooter = e.getEntity();

        // Grant vision if necessary
        if(ticksRemaining <= 0 && this.storage >= this.consumption) {
            Log.log("[ArchEyesCap] ArchEyes granting Vision!", LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SPAMMY);
            this.storage -= this.consumption;
            ticksRemaining += 20;
        }

        // Restock energy if necessary
        if(this.storage < this.consumption) {
            Log.log("[ArchEyesCap] ArchEyes missing Energy, posting Event.", LogType.DEBUG, LogType.SZEIBER_CAP,
                    LogType.SPAMMY);
            int missingEnergy = this.maxStorage - this.storage;
            EnergyConsumptionEvent event = new EnergyConsumptionEvent(shooter, missingEnergy);
            MinecraftForge.EVENT_BUS.post(event);
            Log.log("[ArchEyesCap] Event granted " + (missingEnergy - event.getRemainingAmount()) + " Energy.",
                    LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SPAMMY);
            this.storage += (missingEnergy - event.getRemainingAmount());
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

    @Override
    public EnergyPriority currentConsumptionPrio() {
        return EnergyPriority.FILL_ASAP;
    }

    @Override
    public boolean canStillConsume() {
        return storage < maxStorage;
    }

    @Override
    public int consume() {
        Log.log("[ArchEyesCap] ArchEyes attempting to consume energy!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP, LogType.SPAMMY);
        if(canStillConsume()) {
            storage++;
            Log.log("[ArchEyesCap] ArchEyes consuming energy! Now storing: " + storage, LogType.DEBUG,
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
