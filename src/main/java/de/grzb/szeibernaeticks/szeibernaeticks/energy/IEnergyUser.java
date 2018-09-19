package de.grzb.szeibernaeticks.szeibernaeticks.energy;

import java.util.Collection;

import javax.annotation.Nonnull;

public interface IEnergyUser {
    /**
     * Returns the amount of energy this storer can hold.
     *
     * @return
     */
    int getMaxEnergy();

    /**
     * Returns the amount of energy this user currently holds.
     *
     * @return
     */
    int getCurrentEnergy();

    /**
     * Obtains a list of promises to consume Energy. <br>
     * These represent this users ability to store or use energy produced by
     * other users.
     *
     * @return A promise to produce Energy.
     */
    @Nonnull
    Collection<EnergyPromise.Consumption> promiseStorage();

    /**
     * Obtains a list of promises to produce Energy. <br>
     * These represent this users ability to provide energy to other users.
     *
     * @return A promise to produce Energy.
     */
    @Nonnull
    Collection<EnergyPromise.Production> promiseProduction();
}
