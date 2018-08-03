package main.de.grzb.szeibernaeticks.block;

import main.de.grzb.szeibernaeticks.tileentity.TileEntityGuiContainerAssembler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class BlockTileEntityGuiContainerAssembler extends BlockTileEntityGuiContainerBase {

    public BlockTileEntityGuiContainerAssembler() {
        super("assembler", Material.IRON);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityGuiContainerAssembler(this);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        world.removeTileEntity(pos);
    }

    // method isn't "really" deprecated
    @Override @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override @ParametersAreNonnullByDefault @SuppressWarnings("deprecation")
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        AxisAlignedBB aabb = new AxisAlignedBB(0.0, 0.0, 0.0, 2.0, 0.75, 1.0);
        BlockTileEntityGuiContainerBase.addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        player.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5, player.rotationYaw, -90.0F);
        return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }

}
