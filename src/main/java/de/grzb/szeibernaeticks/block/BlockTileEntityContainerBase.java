package de.grzb.szeibernaeticks.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class BlockTileEntityContainerBase extends BlockTileEntityBase {

    public BlockTileEntityContainerBase(String name, Material material) {
        super(name, material);
    }

    /**
     * Drops inventory contents when the block breaks. Uses
     * {@link net.minecraftforge.common.capabilities.Capability FML's capability
     * system} instead of the {@link net.minecraft.inventory.IInventory
     * IInventory} interface of vanilla inventories.
     */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        IItemHandler inventory = world.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        for(int slot = 0; slot < inventory.getSlots(); slot++) {
            ItemStack itemStack = inventory.getStackInSlot(slot);
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);
}
