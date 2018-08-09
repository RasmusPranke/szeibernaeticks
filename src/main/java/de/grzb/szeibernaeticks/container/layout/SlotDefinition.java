package de.grzb.szeibernaeticks.container.layout;

import de.grzb.szeibernaeticks.container.slot.SlotType;

public class SlotDefinition {
    public SlotType slotType;
    public int x;
    public int y;

    public SlotDefinition(SlotType slotType, int x, int y) {
        this.slotType = slotType;
        this.x = x;
        this.y = y;
    }

}
