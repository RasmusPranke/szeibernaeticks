package main.de.grzb.szeibernaeticks.tileentity;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Registers all tile entities during pre-init.
 *
 * @author yuri
 * @see main.de.grzb.szeibernaeticks.CommonProxy CommonProxy
 */
public final class ModTileEntities {
    public static TileEntity assembler;

    public static void init() {
        assembler = register(new TileEntityGuiContainerAssembler());
    }

    private static <T extends TileEntityBase> T register(T tileEntity) {
        GameRegistry.registerTileEntity(tileEntity.getClass(), new ResourceLocation(Szeibernaeticks.RESOURCE_PREFIX, tileEntity.getName()));
        return tileEntity;
    }

}
