package main.de.grzb.szeibernaeticks.tileentity;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Registers all tile entities during pre-init.
 *
 * @author yuri
 * @see main.de.grzb.szeibernaeticks.CommonProxy CommonProxy
 */
public final class ModTileEntities {
    public static void init() {
        GameRegistry.registerTileEntity(TileEntityGuiContainerAssembler.class, new ResourceLocation(Szeibernaeticks.MOD_ID, "assembler"));
    }
}
