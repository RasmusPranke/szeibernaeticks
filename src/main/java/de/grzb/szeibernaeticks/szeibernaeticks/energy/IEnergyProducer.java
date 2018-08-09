package de.grzb.szeibernaeticks.szeibernaeticks.energy;

/**
 * A Szeibernaetick able to produce Energy <i>upon request</i>. <br>
 * <br>
 * These are explicitly <i>NOT</i> Szeibernaeticks able to produce energy at
 * all. Any Szeibernaetick can post EnergyProductionEvents. IEnergyProducers are
 * asked to produce when an EnergyConsumptionEvent is fired.
 *
 * @author DemRat
 */
public interface IEnergyProducer extends IEnergyStorer {
    /**
     * Returns with what priority this Producer currently needs to get rid of
     * its energy.
     *
     * @return
     */
    EnergyPriority currentProductionPriority();

    /**
     * Returns whether this Producer can still generate Energy this tick.<br>
     * <br>
     * This returns false exactly when produceAdHoc would return 0.
     *
     * @return
     */
    boolean canStillProduce();

    /**
     * Produces one unit of energy.<br>
     * <br>
     * This produces a single unit of energy, if possible. If for whatever
     * reason this can not produce, this returns 0.
     *
     * @return 1 if production was successful, 0 otherwise.
     */
    int produceAdHoc();
}
