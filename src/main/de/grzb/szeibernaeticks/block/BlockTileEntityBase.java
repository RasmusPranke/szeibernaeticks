package main.de.grzb.szeibernaeticks.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockTileEntityBase extends BlockBase implements ITileEntityProvider {

    public BlockTileEntityBase(String name, Material material) {
        super(name, material);
        this.hasTileEntity = true;
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }
}
