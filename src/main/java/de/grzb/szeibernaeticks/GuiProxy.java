package de.grzb.szeibernaeticks;

import de.grzb.szeibernaeticks.client.gui.GuiFactory;
import de.grzb.szeibernaeticks.client.gui.GuiId;
import de.grzb.szeibernaeticks.container.GuiContainerBase;
import de.grzb.szeibernaeticks.tileentity.TileEntityGuiContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {
    private static final GuiId[] GUI_IDS = GuiId.values();

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);

        if(tileEntity instanceof TileEntityGuiContainerBase) {
            return ((TileEntityGuiContainerBase) tileEntity).getContainer(player);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        Object container = this.getServerGuiElement(id, player, world, x, y, z);
        if(container != null) {
            return new GuiFactory((GuiContainerBase) container).getGui(GUI_IDS[id]);
        }
        return null;
    }
}
