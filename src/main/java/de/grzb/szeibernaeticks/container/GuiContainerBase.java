package de.grzb.szeibernaeticks.container;

import de.grzb.szeibernaeticks.container.layout.GuiLayoutDefinition;
import de.grzb.szeibernaeticks.tileentity.TileEntityGuiContainerBase;
import de.grzb.szeibernaeticks.Szeibernaeticks;
import de.grzb.szeibernaeticks.client.gui.GuiId;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Base class to create container GUIs.
 *
 * @author yuri
 */
public abstract class GuiContainerBase extends ContainerBase {
    protected GuiId guiId;
    protected GuiLayoutDefinition layout;

    private ResourceLocation background = new ResourceLocation(Szeibernaeticks.MOD_ID, "textures/gui/container/" + this.tileEntityContainer.getName() + ".png");

    /**
     * Sets up a basic GUI layout. Take care for the player's own inventory and
     * border spacing.
     */
    public GuiContainerBase(TileEntityGuiContainerBase tileEntity, GuiLayoutDefinition layout, GuiId guiId) {
        super(tileEntity);
        this.layout = layout;
        this.addGuiLayout();
        this.guiId = guiId;
    }

    protected void addGuiLayout() {
        for(Slot slot : this.layout.getLayout()) {
            this.addSlotToContainer(slot);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()) {
            ItemStack slotItemStack = slot.getStack();
            itemstack = slotItemStack.copy();

            if(index < this.tileEntityContainer.getContainerSize()) {
                if(!this.mergeItemStack(slotItemStack, this.layout.getContainerSize(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(slotItemStack, 0, this.layout.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if(slotItemStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(EntityPlayer player) {
        return ((TileEntityGuiContainerBase) this.tileEntityContainer).canInteractWith(player);
    }

    public ResourceLocation getGuiBackground() {
        return this.background;
    }

    public GuiLayoutDefinition getLayoutDefinition() {
        return this.layout;
    }

    public GuiId getGuiId() {
        return this.guiId;
    }
}
