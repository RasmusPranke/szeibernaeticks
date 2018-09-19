package de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;
import java.util.Collection;

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
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPromise;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPromise.Production;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyUser;
import de.grzb.szeibernaeticks.szeibernaeticks.handler.GeneratorStomachHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.food.FoodValues;

@SzeiberClass(handler = { GeneratorStomachHandler.class }, item = GeneratorStomach.Item.class)
public class GeneratorStomach extends EnergyUserBase implements ISzeibernaetick, IEnergyUser {

    private class OnOffSwitch extends Switch.BooleanSwitch {

        public OnOffSwitch(ISzeibernaetick sourceSzeiber, String name) {
            super(sourceSzeiber, name);
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
        public boolean isActive() {
            return true;
        }
    }

    @SzeiberClass.Identifier
    public static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "GeneratorStomach");
    @SzeiberClass.ItemInject
    public static final Item item = null;
    private static final BodyPart bodyPart = BodyPart.STOMACH;
    private static final int foodMultiplier = 10;
    private static final int saturationMultiplier = 10;
    private EntityPlayer player; // Required to modify players food values

    private boolean isActive() {
        return activePassive.getValue();
    }

    private boolean isOn() {
        return onOff.getValue();
    }

    private final OnOffSwitch onOff = new OnOffSwitch(this, "OnOff");
    private final ActivePassiveSwitch activePassive = new ActivePassiveSwitch(this, "ActivePassive");

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
    }

    @Override
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public void convertFood(FoodEvent.GetPlayerFoodValues event) {
        if(isOn()) {
            FoodValues values = event.foodValues;
            int energyProduced = values.hunger * foodMultiplier
                    + (int) (values.saturationModifier * saturationMultiplier);
            event.foodValues = new FoodValues(values.hunger / 2, values.saturationModifier / 2);

            Log.log("[GenStomach] Producing energy: " + energyProduced, LogType.DEBUG, LogType.SZEIBER_ENERGY,
                    LogType.SZEIBER_CAP);
            Supply production = new Supply(event.player, energyProduced);
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

    @Override
    public Collection<Production> promiseProduction() {
        Collection<EnergyPromise.Production> list = super.promiseProduction();
        if(isActive() && isOn()) {
            list.add(new EnergyPromise.Production(EnergyPriority.BODILY_HARM) {
                private FoodStats playerFood = player.getFoodStats();

                @Override
                public int getLimit() {
                    int saturationLimit = (int) (playerFood.getSaturationLevel() * saturationMultiplier);
                    int foodLimit = playerFood.getFoodLevel() * foodMultiplier;
                    // Saturation level can be negative!
                    if(saturationLimit < 0) {
                        saturationLimit = 0;
                    }
                    return foodLimit + saturationLimit;
                }

                @Override
                public void satisfy(int amount) {
                    int production = 0;

                    Log.log("[GenStomach] Requested amount: " + amount, LogType.DEBUG, LogType.SZEIBER_ENERGY,
                            LogType.SZEIBER_CAP);

                    float saturationConsumption = (float) Math.ceil(((double) amount) / saturationMultiplier);
                    // Saturation level can be negative!
                    if(playerFood.getSaturationLevel() < saturationConsumption) {
                        saturationConsumption = playerFood.getSaturationLevel();
                    }
                    if(saturationConsumption < 0) {
                        saturationConsumption = 0;
                    }
                    production += saturationConsumption * saturationMultiplier;

                    int foodConsumption = (int) Math.ceil((((double) amount) - production) / foodMultiplier);
                    production += foodConsumption * foodMultiplier;

                    MinecraftForge.EVENT_BUS.post(new Supply(player, production - amount));
                    playerFood.setFoodSaturationLevel(playerFood.getSaturationLevel() - saturationConsumption);
                    Log.log("[GenStomach] Consuming Saturation: " + saturationConsumption, LogType.DEBUG,
                            LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP);
                    playerFood.setFoodLevel(playerFood.getFoodLevel() - foodConsumption);
                    Log.log("[GenStomach] Consuming Saturation: " + foodConsumption, LogType.DEBUG,
                            LogType.SZEIBER_ENERGY, LogType.SZEIBER_CAP);
                }
            });
        }
        return list;
    }

    @Override
    public String toNiceString() {
        return "Generator Stomach";
    }

}
