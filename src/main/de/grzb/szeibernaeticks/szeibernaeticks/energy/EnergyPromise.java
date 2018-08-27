package main.de.grzb.szeibernaeticks.szeibernaeticks.energy;

public abstract class EnergyPromise {
    public final EnergyPriority priority;

    protected EnergyPromise(EnergyPriority priority) {
        this.priority = priority;
    }

    public abstract int getLimit();

    public abstract void satisfy(int amount);

    public static abstract class Production extends EnergyPromise {

        protected Production(EnergyPriority priority) {
            super(priority);
        }
    }

    public static abstract class Consumption extends EnergyPromise {

        protected Consumption(EnergyPriority priority) {
            super(priority);
        }
    }
}
