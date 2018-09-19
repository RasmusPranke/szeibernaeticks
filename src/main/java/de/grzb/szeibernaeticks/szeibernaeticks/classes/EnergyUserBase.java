package de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;
import java.util.Collection;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPromise;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPromise.Consumption;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPromise.Production;
import de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyUser;

public abstract class EnergyUserBase implements ISzeibernaetick, IEnergyUser {
    protected int maxStorage = 100;
    protected int storage = 0;

    @Override
    public int getMaxEnergy() {
        return maxStorage;
    }

    @Override
    public int getCurrentEnergy() {
        return storage;
    }

    @Override
    public Collection<Consumption> promiseStorage() {
        Collection<Consumption> list = new ArrayList<>();
        list.add(new EnergyPromise.Consumption(EnergyPriority.FREE) {

            @Override
            public int getLimit() {
                return maxStorage - storage;
            }

            @Override
            public void satisfy(int amount) {
                Log.log("Adding " + amount + " to " + this.toString(), LogType.DEBUG, LogType.SZEIBER_HANDLER,
                        LogType.SZEIBER_ENERGY, LogType.SPECIFIC);
                storage += amount;
            }
        });
        return list;
    }

    @Override
    public Collection<Production> promiseProduction() {
        Collection<Production> list = new ArrayList<>();
        list.add(new EnergyPromise.Production(EnergyPriority.FREE) {

            @Override
            public int getLimit() {
                return storage;
            }

            @Override
            public void satisfy(int amount) {
                Log.log("Removing " + amount + " from " + this.toString(), LogType.DEBUG, LogType.SZEIBER_HANDLER,
                        LogType.SZEIBER_ENERGY, LogType.SPECIFIC);
                storage -= amount;
            }
        });
        return list;
    }

}
