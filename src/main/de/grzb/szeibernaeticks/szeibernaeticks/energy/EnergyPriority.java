package main.de.grzb.szeibernaeticks.szeibernaeticks.energy;

public enum EnergyPriority {
    /**
     *
     */
    FREE(2),
    /**
    *
    */
    BODILY_HARM(1),
    /**
    *
    */
    REPLENISHABLE_RESOURCE(0),
    /**
    *
    */
    DEPLETABLE_RESOURCE(-1),
    /**
    *
    */
    STIRCTLY_LIMITED_RESOURCE(-2);

    public final int priority;

    EnergyPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Returns true if the arguments prio is lower than this ones.
     *
     * @return Duh.
     */
    public boolean largerThan(EnergyPriority energyPriority) {
        return this.priority > energyPriority.priority;
    }
}
