package de.grzb.szeibernaeticks.szeibernaeticks.energy;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public abstract class EnergyEvent extends Event {

    protected final Entity eventEntity;
    protected int amount;
    protected boolean met;

    public EnergyEvent(Entity entity, int amount) {
        this.eventEntity = entity;
        this.amount = amount;
    }

    /**
     * How much energy demand is still left.
     *
     * @return
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * The entity this event happens on.
     *
     * @return This events entity
     */
    public Entity getEntity() {
        return this.eventEntity;
    }

    public void setMet(boolean met) {
        this.met = met;
    }

    public boolean isMet() {
        return !this.isCanceled() && met;
    }

    /**
     * Fired whenever an {@code IProducer} produces Energy outside of immediate
     * demand, i.e. not in response to an {@code Consumption}.
     */
    public static class Supply extends EnergyEvent {
        public Supply(Entity entity, int amount) {
            super(entity, amount);
        }
    }

    public static class Demand extends EnergyEvent {
        public Demand(Entity entity, int amount) {
            super(entity, amount);
        }
    }
}
