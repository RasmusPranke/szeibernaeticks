package de.grzb.szeibernaeticks.container.slot;

import javax.annotation.Nonnull;

import de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickCapabilityProvider;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBodyPart extends SlotItemHandler {
    private final BodyPart bodyPart;

    public SlotBodyPart(IItemHandler itemHandler, int index, int xPosition, int yPosition, BodyPart bodyPart) {
        super(itemHandler, index, xPosition, yPosition);
        this.bodyPart = bodyPart;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        ISzeibernaetick capability = stack.getCapability(SzeibernaetickCapabilityProvider.SZEIBER_CAP, null);

        if(super.isItemValid(stack) && capability != null) {
            return capability.getBodyPart().equals(this.bodyPart);
        }

        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return 1;
    }

}
