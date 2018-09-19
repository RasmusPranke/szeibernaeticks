package de.grzb.szeibernaeticks;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.render.FakeRenderFactory;
import de.grzb.szeibernaeticks.render.RenderBlockMarkerFactory;
import de.grzb.szeibernaeticks.szeibernaeticks.control.SwitchControl;
import de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityArrowFake;
import de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityBlockMarker;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
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
        ClientRegistry.registerKeyBinding(SwitchControl.SzeiberHUDKey);
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
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(Szeibernaeticks.RESOURCE_PREFIX + id, "inventory"));
    }
}
