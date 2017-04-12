package main.de.grzb.szeibernaeticks;

import main.de.grzb.szeibernaeticks.block.ModBlocks;
import main.de.grzb.szeibernaeticks.crafting.ModRecipes;
import main.de.grzb.szeibernaeticks.item.ModItems;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetickStorageCapability;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.SzeibernaetickCapabilityAttacher;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.SzeibernaetickStorageCapabilityStorage;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.SzeibernaetickStorage;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickEventMetalBones;
import main.de.grzb.szeibernaeticks.tileentity.ModTileEntities;
import main.de.grzb.szeibernaeticks.world.ModWorldGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Executes code on both, server and client.
 * 
 * @see ClientProxy
 * @author yuri
 */
public class CommonProxy {

  public void preInit(FMLPreInitializationEvent e) {
    Szeibernaeticks.setLogger(e.getModLog());
    Szeibernaeticks.getLogger().debug("CommonProxy, preInit.");
    ModItems.init();
    ModBlocks.init();
    ModTileEntities.init();
  }

  public void init(FMLInitializationEvent e) {
    ModRecipes.init();
    GameRegistry.registerWorldGenerator(new ModWorldGenerator(), 0);
    NetworkRegistry.INSTANCE.registerGuiHandler(Szeibernaeticks.instance, new GuiProxy());

    CapabilityManager.INSTANCE.register(ISzeibernaetickStorageCapability.class, new SzeibernaetickStorageCapabilityStorage(), SzeibernaetickStorage.class);

    MinecraftForge.EVENT_BUS.register(new SzeibernaetickEventMetalBones((ISzeibernaetick) ModItems.metal_bones));
    MinecraftForge.EVENT_BUS.register(new SzeibernaetickCapabilityAttacher());
  }

  public void postInit(FMLPostInitializationEvent e) {

  }

  public void registerItemRenderer(Item item, int meta, String id) {
    // method stub, client-only method
  }
}
