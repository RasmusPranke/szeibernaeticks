package main.de.grzb.szeibernaeticks.szeibernaeticks.energy;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class EnergyEvent extends Event {

    protected final Entity eventEntity;
    protected int remainingAmount;

    public EnergyEvent(Entity entity, int amount) {
        eventEntity = entity;
        remainingAmount = amount;
    }

    /**
     * How much energy demand is still left.
     *
     * @return
     */
    public int getRemainingAmount() {
        return remainingAmount;
    }

    /**
     * The entity this event happens on.
     *
     * @return This events entity
     */
    public Entity getEntity() {
        return eventEntity;
    }
}
