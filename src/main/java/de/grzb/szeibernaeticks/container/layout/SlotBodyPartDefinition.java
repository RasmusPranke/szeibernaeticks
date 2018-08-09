package de.grzb.szeibernaeticks.container.layout;

import de.grzb.szeibernaeticks.container.slot.SlotType;
import de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import net.minecraft.item.ItemStack;

public class SlotBodyPartDefinition extends SlotDefinition {
    public BodyPart bodyPart;
    public ItemStack installedSzeibernaetick;

    public SlotBodyPartDefinition(SlotType slotType, int x, int y, BodyPart bodyPart, ItemStack installedSzeibernaetick) {
        super(slotType, x, y);
        this.bodyPart = bodyPart;
        this.installedSzeibernaetick = installedSzeibernaetick;
    }

    public SlotBodyPartDefinition(SlotType slotType, int x, int y, BodyPart bodyPart) {
        this(slotType, x, y, bodyPart, ItemStack.EMPTY);
    }
}
