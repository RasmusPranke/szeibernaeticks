package de.grzb.szeibernaeticks.szeibernaeticks.energy;

public interface IEnergyConsumer extends IEnergyStorer {
    /**
     * Returns with what priority this Consumer currently needs to consume
     * energy.
     *
     * @return
     */
    EnergyPriority currentConsumptionPrio();

    /**
     * Returns whether this Consumer can still consume energy this tick.<br>
     * <br>
     * This returns false exactly when consume() would return 0.
     *
     * @return Duh
     */
    boolean canStillConsume();

    /**
     * Consumes a single unit of energy and returns 1.<br>
     * <br>
     * This consumes a single unit of energy, if possible. If for whatever
     * reason this cannot consume, this returns 0.
     *
     * @return 1 if consumption was successful, 0 otherwise.
     */
    int consume();

}
