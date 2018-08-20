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
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyProductionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyProducer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.GeneratorStomachHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class GeneratorStomach implements ISzeibernaetick, IEnergyProducer, IEnergyConsumer {
    private static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "GeneratorStomach");
    private static final BodyPart bodyPart = BodyPart.STOMACH;
    private int storage;
    private int maxStorage;

    @Override
    public SzeibernaetickIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public NBTTagCompound toNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        return;
    }

    @Override
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    // IEnergyConsumer Implementation

    @Override
    public EnergyPriority currentConsumptionPrio() {
        return EnergyPriority.EMPTY_ASAP;
    }

    @Override
    public boolean canStillConsume() {
        return this.storage < this.maxStorage;
    }

    @Override
    public int consume() {
        Log.log("[GenStomach] GenStomach attempting to consume energy!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP, LogType.SPAMMY);
        if(this.canStillConsume()) {
            this.storage++;
            Log.log("[GenStomach] GenStomach consuming energy! Now storing: " + this.storage, LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP, LogType.SPAMMY);
            return 1;
        }
        return 0;
    }

    // IEnergyProducer Implementation

    @Override
    public EnergyPriority currentProductionPriority() {
        return EnergyPriority.EMPTY_ASAP;
    }

    @Override
    public boolean canStillProduce() {
        return this.storage > 0;
    }

    @Override
    public int produceAdHoc() {
        Log.log("[GenStomach] GenStomach attempting to produce energy!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP, LogType.SPAMMY);
        if(this.canStillProduce()) {
            this.storage--;
            Log.log("[GenStomach] GenStomach produced energy! Remaining storage: " + this.storage, LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP, LogType.SPAMMY);
            return 1;
        }
        return 0;
    }

    public int produce(int energyProduced, Entity entity) {
        Log.log("[GenStomach] Producing energy: " + energyProduced, LogType.DEBUG, LogType.SZEIBER_ENERGY,
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
        int retrieved = 0;

        while(produceAdHoc() != 0) {
            retrieved++;
        }

        return retrieved;
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
            GeneratorStomach cap = new GeneratorStomach();
            if(nbt != null) {
                cap.fromNBT(nbt);
            }
            return new SzeibernaetickCapabilityProvider(cap);
        }
    }

    public static void register(ModItems.RegisteringMethod method) {
        Item.item = method.registerSzeibernaetick(new Item(), GeneratorStomachHandler.class);
    }

}
