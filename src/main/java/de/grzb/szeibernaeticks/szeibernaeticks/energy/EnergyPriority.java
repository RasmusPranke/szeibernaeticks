package de.grzb.szeibernaeticks.szeibernaeticks.energy;

public enum EnergyPriority {
    /**
     *
     */
    FILL_ASAP(2), /**
     *
     */
    FILL_FAST(1), /**
     *
     */
    DONT_CARE(0), /**
     *
     */
    EMPTY_FAST(-1), /**
     *
     */
    EMPTY_ASAP(-2);

    private int priority;

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
