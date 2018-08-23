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
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyProductionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyProducer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.handler.GeneratorStomachHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.food.FoodValues;

@Szeibernaetick(handler = { GeneratorStomachHandler.class }, item = GeneratorStomach.Item.class)
public class GeneratorStomach implements ISzeibernaetick, IEnergyProducer, IEnergyConsumer {

    private class OnOffSwitch extends Switch.BooleanSwitch {

        public OnOffSwitch(ISzeibernaetick sourceSzeiber, String name) {
            super(sourceSzeiber, name);
        }

        @Override
        protected boolean getValue() {
            return isOn;
        }

        @Override
        protected void setValue(boolean val) {
            isOn = val;
        }

        @Override
        public boolean isActive() {
            return true;
        }
    }

    private class ActivePassiveSwitch extends Switch.BooleanSwitch {

        public ActivePassiveSwitch(ISzeibernaetick sourceSzeiber, String name) {
            super(sourceSzeiber, name);
        }

        @Override
        protected boolean getValue() {
            return isActive;
        }

        @Override
        protected void setValue(boolean val) {
            isActive = val;
        }

        @Override
        public boolean isActive() {
            return true;
        }
    }

    @Szeibernaetick.Identifier
    public static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "GeneratorStomach");
    @Szeibernaetick.ItemInject
    public static final Item item = null;
    private static final BodyPart bodyPart = BodyPart.STOMACH;
    private static final int foodMultiplier = 10;
    private static final int saturationMultiplier = 10;
    private EntityPlayer player; // Required to modify players food values
    private int storage;
    private int maxStorage;
    private boolean isActive = true;
    private boolean isOn = true;
    private OnOffSwitch onOff = new OnOffSwitch(this, "OnOff");
    private ActivePassiveSwitch activePassive = new ActivePassiveSwitch(this, "ActivePassive");

    @Override
    public SzeibernaetickIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(onOff.getIdentifier(), onOff.GetState());
        tag.setInteger(activePassive.getIdentifier(), activePassive.GetState());
        return tag;
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        if(nbt.hasKey(onOff.getIdentifier())) {
            onOff.SetState(nbt.getInteger(onOff.getIdentifier()));
        }
        if(nbt.hasKey(activePassive.getIdentifier())) {
            activePassive.SetState(nbt.getInteger(activePassive.getIdentifier()));
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

    private boolean isStorageEmpty() {
        return storage == 0;
    }

    private boolean canConsumeFood() {
        if(isOn && isActive && player != null) {
            return player.getFoodStats().getFoodLevel() > 0;
        }
        return false;
    }

    @Override
    public boolean canStillProduce() {
        return !isStorageEmpty() || canConsumeFood();
    }

    @Override
    public int produceAdHoc() {
        Log.log("[GenStomach] GenStomach attempting to produce energy!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_CAP, LogType.SPAMMY);
        if(isStorageEmpty()) {
            if(canConsumeFood()) {
                FoodStats playerFood = player.getFoodStats();
                int foodLevel = playerFood.getFoodLevel();
                storage += foodMultiplier;
                playerFood.setFoodLevel(foodLevel - 1);
            }
        }

        if(!isStorageEmpty()) {
            this.storage--;
            Log.log("[GenStomach] GenStomach produced energy! Remaining storage: " + this.storage, LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP, LogType.SPAMMY);
            return 1;
        }

        return 0;
    }

    public void produce(FoodEvent.GetPlayerFoodValues event) {
        Log.log("[GenStomach] Trying to produce energy: " + isOn, LogType.TEMP);
        if(isOn) {
            FoodValues values = event.foodValues;
            int energyProduced = values.hunger * foodMultiplier
                    + (int) (values.saturationModifier * saturationMultiplier);
            event.foodValues = new FoodValues(values.hunger / 2, values.saturationModifier / 2);

            Log.log("[GenStomach] Producing energy: " + energyProduced, LogType.DEBUG, LogType.SZEIBER_ENERGY,
                    LogType.SZEIBER_CAP);
            EnergyProductionEvent production = new EnergyProductionEvent(event.player, energyProduced);
            MinecraftForge.EVENT_BUS.post(production);
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
        ArrayList<Switch> switches = new ArrayList<Switch>();
        switches.add(onOff);
        switches.add(activePassive);
        return switches;
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
            GeneratorStomach cap = new GeneratorStomach();
            if(nbt != null) {
                cap.fromNBT(nbt);
            }
            return new SzeibernaetickCapabilityProvider(cap);
        }
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

}
