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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class RadarEyes extends SzeibernaetickBase implements IEnergyConsumer {
    private static final String identifier = Szeibernaeticks.MOD_ID + ":RadEyes";
    private int maxStorage = 20;
    private int storage = 0;
    private int consumption = 1;
    private int ticksRemaining = 0;
    private boolean active = false;
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

    public void grantVision(LivingUpdateEvent e) {
        Log.log("[RadEyesCap] ArchEyes attempting to grant vision!", LogType.DEBUG, LogType.SZEIBER_CAP,
                LogType.SPAMMY);

        Entity shooter = e.getEntity();

        // Grant vision if necessary
        if(ticksRemaining <= 0 && this.storage >= this.consumption) {
            Log.log("[RadEyesCap] ArchEyes granting Vision!", LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SPAMMY);
            this.storage -= this.consumption;
            ticksRemaining += 20;
        }

        // Restock energy if necessary
        if(this.storage < this.consumption) {
            Log.log("[RadEyesCap] Missing Energy, posting Event.", LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SPAMMY);
            int missingEnergy = this.maxStorage - this.storage;
            EnergyConsumptionEvent event = new EnergyConsumptionEvent(shooter, missingEnergy);
            MinecraftForge.EVENT_BUS.post(event);
            Log.log("[RadEyesCap] Event granted " + (missingEnergy - event.getRemainingAmount()) + " Energy.",
                    LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SPAMMY);
            this.storage += (missingEnergy - event.getRemainingAmount());
        }

        // Check whether vision should still be granted
        if(ticksRemaining > 0) {
            --ticksRemaining;
            Log.log("[RadEyesCap] Ticks Remaining!.", LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SPAMMY);
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
                    Log.log("[RadEyesCap] Found Living Entity of type: " + oneOfAll.getClass(), LogType.DEBUG,
                            LogType.SZEIBER_CAP, LogType.SPAMMY);
                    ((EntityLivingBase) oneOfAll).setGlowing(active);
                }
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
