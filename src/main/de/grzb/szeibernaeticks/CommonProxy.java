package main.de.grzb.szeibernaeticks;

import main.de.grzb.szeibernaeticks.block.ModBlocks;
import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.crafting.ModRecipes;
import main.de.grzb.szeibernaeticks.item.ModItems;
import main.de.grzb.szeibernaeticks.networking.GuiMessage;
import main.de.grzb.szeibernaeticks.networking.NetworkWrapper;
import main.de.grzb.szeibernaeticks.networking.SzeiberCapMessage;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.Armoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryStorage;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.CapabilityAttacher;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.CapabilityStorage;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.DummyDefault;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityBlockMarker;
import main.de.grzb.szeibernaeticks.szeibernaeticks.overlay.EnergyOverlay;
import main.de.grzb.szeibernaeticks.tileentity.ModTileEntities;
import main.de.grzb.szeibernaeticks.world.ModWorldGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Executes code on both, server and client.
 *
 * @author yuri
 * @see ClientProxy
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        Szeibernaeticks.setLogger(e.getModLog());
        Log.getLogger().setForgeLogger(e.getModLog());

        Log.log("PreInit!", LogType.SETUP, LogType.INFO);
        ModItems.init();
        ModBlocks.init();
        ModTileEntities.init();

        NetworkWrapper.INSTANCE.registerMessage(SzeiberCapMessage.SzeiberCapMessageHandler.class,
                SzeiberCapMessage.class, NetworkWrapper.getId(), Side.CLIENT);
        NetworkWrapper.INSTANCE.registerMessage(GuiMessage.GuiMessageHandler.class, GuiMessage.class,
                NetworkWrapper.getId(), Side.SERVER);

        MinecraftForge.EVENT_BUS.register(new CapabilityAttacher());
        MinecraftForge.EVENT_BUS.register(new LogType.DebugConfig());

        // TODO: Change this, maybe put somewhere else
        EntityRegistry.registerModEntity(new ResourceLocation(Szeibernaeticks.RESOURCE_PREFIX + "block_marker"),
                EntityBlockMarker.class, "block_marker", 0, Szeibernaeticks.instance, 20, 3, false);
    }

    public void init(FMLInitializationEvent e) {
        ModRecipes.init();
        GameRegistry.registerWorldGenerator(new ModWorldGenerator(), 0);
        NetworkRegistry.INSTANCE.registerGuiHandler(Szeibernaeticks.instance, new GuiProxy());

        CapabilityManager.INSTANCE.register(IArmoury.class, new ArmouryStorage(), Armoury::new);
        CapabilityManager.INSTANCE.register(ISzeibernaetick.class, new CapabilityStorage(), DummyDefault::new);
    }

    public void postInit(FMLPostInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new EnergyOverlay());
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        // method stub, client-only method
    }
}
