package de.grzb.szeibernaeticks.container.slot;

import de.grzb.szeibernaeticks.container.layout.SlotDefinition;
import de.grzb.szeibernaeticks.container.layout.SlotBodyPartDefinition;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFactory {

    public static SlotItemHandler getNewSlotItemHandler(SlotDefinition slotDefinition, IItemHandler itemHandler, int index, int x, int y) {
        SlotItemHandler slot;
        switch(slotDefinition.slotType) {
            case BODYPART:
                SlotBodyPartDefinition slotBodyPartDefinition = (SlotBodyPartDefinition) slotDefinition;
                slot = new SlotBodyPart(itemHandler, index, x, y, slotBodyPartDefinition.bodyPart);
                slot.putStack(slotBodyPartDefinition.installedSzeibernaetick);
                break;
            default:
                slot = new SlotItemHandler(itemHandler, index, x, y);
                break;
        }

        return slot;
    }

}
