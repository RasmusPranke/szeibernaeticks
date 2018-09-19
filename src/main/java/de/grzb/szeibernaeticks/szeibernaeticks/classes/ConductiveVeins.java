package de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

import io.netty.util.internal.ConcurrentSet;
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
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyEvent;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPromise;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyUser;
import de.grzb.szeibernaeticks.szeibernaeticks.handler.ConductiveVeinsHandler;
import de.grzb.szeibernaeticks.utility.Wrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

@SzeiberClass(handler = ConductiveVeinsHandler.class, item = ConductiveVeins.Item.class)
public class ConductiveVeins implements ISzeibernaetick {
    @SzeiberClass.Identifier
    public static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "CondVeins");
    @SzeiberClass.ItemInject
    public static final Item item = null;
    private static final BodyPart bodyPart = BodyPart.VEINS;
    private ConcurrentSet<IEnergyUser> users = new ConcurrentSet<IEnergyUser>();

    public ConductiveVeins() {
    }

    public void register(ISzeibernaetick szeiber) {
        Log.log("Adding " + szeiber.getIdentifier() + " to the Energy Network.", LogType.DEBUG, LogType.SZEIBER_HANDLER,
                LogType.SZEIBER_ENERGY);
        if(szeiber instanceof IEnergyUser) {
            Log.log("It's a Producer.", LogType.DEBUG, LogType.SZEIBER_HANDLER, LogType.SZEIBER_ENERGY,
                    LogType.SPECIFIC);
            users.add((IEnergyUser) szeiber);
        }
    }

    public void unregister(ISzeibernaetick szeiber) {
        Log.log("Removing " + szeiber.getIdentifier() + " from the Energy Network.", LogType.DEBUG,
                LogType.SZEIBER_HANDLER, LogType.SZEIBER_ENERGY);

        if(szeiber instanceof IEnergyUser) {
            Log.log("It's a Producer.", LogType.DEBUG, LogType.SZEIBER_HANDLER, LogType.SZEIBER_ENERGY,
                    LogType.SPECIFIC);
            users.remove(szeiber);
        }
    }

    @Override
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    /**
     * Handles the given Consumption
     *
     * @param e
     *            The given Event.
     */
    public void handleDemandEvent(EnergyEvent.Demand e) {
        // TODO: Debug this entire thing!
        Log.log("[ConVeins] Demanding Energy on Entity: " + e.getEntity().getName(), LogType.DEBUG, LogType.SZEIBER_CAP,
                LogType.SZEIBER_ENERGY, LogType.SPAMMY);

        ArrayList<EnergyPromise> promises = getProductionPromises();
        Log.log("[ConVeins] Found " + promises.size() + " promises.", LogType.DEBUG, LogType.SZEIBER_CAP,
                LogType.SZEIBER_ENERGY, LogType.SPAMMY);
        boolean satisfied = satisfy(promises, e.getAmount());
        Log.log("[ConVeins] Met: " + satisfied, LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SZEIBER_ENERGY,
                LogType.SPAMMY);
        e.setMet(satisfied);
    }

    /**
     * Handles the given Production.
     *
     * @param e
     *            The given Event.
     */
    public void handleSupplyEvent(EnergyEvent.Supply e) {
        Log.log("[ConVeins] Supplying Energy on Entity: " + e.getEntity().getName(), LogType.DEBUG, LogType.SZEIBER_CAP,
                LogType.SZEIBER_ENERGY, LogType.SPAMMY);

        ArrayList<EnergyPromise> promises = getConsumptionPromises();
        boolean satisfied = satisfy(promises, e.getAmount());
        Log.log("[ConVeins] Met: " + satisfied, LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SZEIBER_ENERGY,
                LogType.SPAMMY);
        e.setMet(satisfied);
    }

    private boolean satisfy(ArrayList<EnergyPromise> promises, int energyRequired) {
        if(energyRequired <= 0) {
            return true;
        }
        else if(!canSatisfy(promises, energyRequired)) {
            Log.log("[ConVeins] Can't be satisfied!", LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SZEIBER_ENERGY,
                    LogType.SPAMMY);
            return false;
        }

        Map<EnergyPriority, Collection<EnergyPromise>> sortedPromises = sortPromises(promises);

        EnergyPriority[] sortedPriorities = EnergyPriority.values();

        Arrays.sort(sortedPriorities, 0, sortedPriorities.length, new Comparator<EnergyPriority>() {
            @Override
            public int compare(EnergyPriority o1, EnergyPriority o2) {
                return o2.priority - o1.priority;
            }
        });
        for(EnergyPriority prio : sortedPriorities) {
            Iterable<EnergyPromise> pList = sortedPromises.get(prio);
            // We know that all together can satisfy the energy.
            // If the current Prio can't, we just take all their energy.
            if(!canSatisfy(pList, energyRequired)) {
                Log.log("[ConVeins] Prio " + prio + " can't satisfy " + energyRequired, LogType.DEBUG,
                        LogType.SZEIBER_CAP, LogType.SZEIBER_ENERGY, LogType.SPAMMY);
                pList.forEach(new Consumer<EnergyPromise>() {
                    @Override
                    public void accept(EnergyPromise t) {
                        t.satisfy(t.getLimit());
                    }
                });
                energyRequired -= sumAvailableEnergy(pList);
            }
            else {
                int available = sumAvailableEnergy(pList);
                Log.log("[ConVeins] Prio " + prio + " can satisfy " + energyRequired + " because it has " + available
                        + " energy.", LogType.DEBUG, LogType.SZEIBER_CAP, LogType.SZEIBER_ENERGY, LogType.SPAMMY);
                double ratio = ((double) energyRequired) / available;
                Log.log("[ConVeins] This works out to a ratio of " + Double.toString(ratio) + ".", LogType.DEBUG,
                        LogType.SZEIBER_CAP, LogType.SZEIBER_ENERGY, LogType.SPAMMY);
                double overflow = 0;
                for(EnergyPromise p : pList) {
                    int overflowBonus = 0;
                    double value = ratio * p.getLimit();
                    Log.log("[ConVeins] Reported Limit is " + p.getLimit() + ".", LogType.DEBUG, LogType.SZEIBER_CAP,
                            LogType.SZEIBER_ENERGY, LogType.SPAMMY);
                    int rounded = (int) value;
                    overflow += value - rounded;
                    Log.log("[ConVeins] Calculated to ask for " + value + " which rounds to " + rounded
                            + " and yields an overflow of " + (value - rounded) + ".", LogType.DEBUG,
                            LogType.SZEIBER_CAP, LogType.SZEIBER_ENERGY, LogType.SPAMMY);
                    // To negate rounding-based off-by-one errors
                    while(overflow > 0.9) {
                        overflowBonus += 1;
                        overflow -= 0.9;
                    }
                    Log.log("[ConVeins] Asking " + p + " to satisfy " + (overflow + rounded), LogType.DEBUG,
                            LogType.SZEIBER_CAP, LogType.SZEIBER_ENERGY, LogType.SPAMMY);
                    p.satisfy(rounded + overflowBonus);
                }

                energyRequired = 0;
            }

            if(energyRequired <= 0) {
                break;
            }
        }
        return true;
    }

    private ArrayList<EnergyPromise> getConsumptionPromises() {
        ArrayList<EnergyPromise> consumptions = new ArrayList<>();
        users.forEach(new Consumer<IEnergyUser>() {
            @Override
            public void accept(IEnergyUser t) {
                consumptions.addAll(t.promiseStorage());
            }
        });
        return consumptions;
    }

    private ArrayList<EnergyPromise> getProductionPromises() {
        ArrayList<EnergyPromise> productions = new ArrayList<>();
        users.forEach(new Consumer<IEnergyUser>() {
            @Override
            public void accept(IEnergyUser t) {
                productions.addAll(t.promiseProduction());
            }
        });
        return productions;
    }

    private Map<EnergyPriority, Collection<EnergyPromise>> sortPromises(Iterable<EnergyPromise> promises) {
        Map<EnergyPriority, Collection<EnergyPromise>> sortedPromises = new HashMap<>();

        EnergyPriority[] sortedPriorities = EnergyPriority.values();
        for(EnergyPriority prio : sortedPriorities) {
            ArrayList<EnergyPromise> list = new ArrayList<>();
            sortedPromises.put(prio, list);
        }
        for(EnergyPromise p : promises) {
            sortedPromises.get(p.priority).add(p);
        }

        return sortedPromises;
    }

    private boolean canSatisfy(Iterable<? extends EnergyPromise> promises, int energyCount) {
        return sumAvailableEnergy(promises) >= energyCount;
    }

    private int sumAvailableEnergy(Iterable<? extends EnergyPromise> users) {
        Wrapper<Integer> wrapper = new Wrapper<Integer>(0);
        users.forEach(new Consumer<EnergyPromise>() {
            @Override
            public void accept(EnergyPromise arg0) {
                wrapper.val += arg0.getLimit();
            }
        });
        return wrapper.val;
    }

    /**
     * Returns a collection containing all registered IEnergyStorers.
     */
    public Collection<IEnergyUser> getEnergyUsers() {
        return new HashSet<>(users);
    }

    /**
     * Returns the amount of energy currently existing within all
     * Szeibernaeticks attached to these veins.
     *
     * @return
     */
    public int getCurrentEnergy() {
        int total = 0;

        for(IEnergyUser s : getEnergyUsers()) {
            total += s.getCurrentEnergy();
        }

        return total;
    }

    /**
     * Returns the amount of energy storable within all Szeibernaeticks attached
     * to these veins.
     *
     * @return
     */
    public int getMaxEnergy() {
        int total = 0;

        for(IEnergyUser s : getEnergyUsers()) {
            total += s.getMaxEnergy();
        }

        return total;
    }

    @Override
    public Iterable<Switch> getSwitches() {
        return new ArrayList<>();
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
            ConductiveVeins cap = new ConductiveVeins();
            if(nbt != null) {
                cap.fromNBT(nbt);
            }
            return new SzeibernaetickCapabilityProvider(cap);
        }
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
    public SzeibernaetickIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public String toNiceString() {
        return "Conductive Veins";
    }
}
