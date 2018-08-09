package de.grzb.szeibernaeticks.item.itemstack;

import de.grzb.szeibernaeticks.tileentity.TileEntityContainerBase;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackTileEntityHandler extends ItemStackHandler {
    private TileEntityContainerBase container;

    public ItemStackTileEntityHandler(int size, TileEntityContainerBase container) {
        super(size);
        this.container = container;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.container.markDirty();
    }
}
