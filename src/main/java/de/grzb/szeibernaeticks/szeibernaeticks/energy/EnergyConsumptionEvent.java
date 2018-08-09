package de.grzb.szeibernaeticks.szeibernaeticks.energy;

import net.minecraft.entity.Entity;

public class EnergyConsumptionEvent extends EnergyEvent {

    public EnergyConsumptionEvent(Entity entity, int amount) {
        super(entity, amount);
    }

    /**
     * Covers the given amount of the demand.<br>
     * <br>
     * This can only reduce the remaining demand to 0.
     *
     * @param amount The amount attempted to cover.
     * @return How much energy could not be consumed.
     */
    public int cover(int amount) {
        this.remainingAmount = this.remainingAmount - amount;
        amount = 0;

        // You cannot consume more than is produced!
        if(this.remainingAmount < 0) {
            amount = -this.remainingAmount;
            this.remainingAmount = 0;
        }
        return amount;
    }
}
