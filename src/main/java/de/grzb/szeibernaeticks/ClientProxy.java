package de.grzb.szeibernaeticks;

import com.google.common.collect.ImmutableMap;
import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.render.FakeRenderFactory;
import de.grzb.szeibernaeticks.render.RenderBlockMarkerFactory;
import de.grzb.szeibernaeticks.tileentity.TileEntityGuiContainerAssembler;
import de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityArrowFake;
import de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityBlockMarker;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.animation.AnimationTESR;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Executes code only on the client side.
 *
 * @author yuri
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        Log.log("Client side Preinit!", LogType.SETUP);

        RenderingRegistry.registerEntityRenderingHandler(EntityArrowFake.class, new FakeRenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityBlockMarker.class, new RenderBlockMarkerFactory());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGuiContainerAssembler.class, new AnimationTESR<>());
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Szeibernaeticks.MOD_ID, id), "inventory"));
    }

    @Override
    public IAnimationStateMachine loadASM(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
        return ModelLoaderRegistry.loadASM(location, parameters);
    }
}
