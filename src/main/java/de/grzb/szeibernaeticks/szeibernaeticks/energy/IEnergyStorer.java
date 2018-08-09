package de.grzb.szeibernaeticks.szeibernaeticks.energy;

public interface IEnergyStorer {
    /**
     * Returns the amount of energy this storer can hold.
     *
     * @return
     */
    int getMaxEnergy();

    /**
     * Returns the amount of energy this storer currently holds.
     *
     * @return
     */
    int getCurrentEnergy();

    /**
     * Attempts to store the given amount.
     *
     * @ensure amount >= 0
     *
     * @param amountToStore
     *            How much should be stored.
     * @return The amount actually stored.
     */
    int store(int amountToStore);

    /**
     * Attempts to retrieve the given amount.
     *
     * @ensure amount >= 0
     *
     * @param amountToRetrieve
     *            How much should be retrieved.
     * @return The amount actually retrieved.
     */
    int retrieve(int amountToRetrieve);
}
