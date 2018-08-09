package de.grzb.szeibernaeticks;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Szeibernaeticks.MOD_ID, version = Szeibernaeticks.VERSION)
@Mod.EventBusSubscriber
public class Szeibernaeticks {

    public static final String MOD_ID = "szeibernaeticks";
    public static final String VERSION = "0.1";

    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    protected static void setLogger(Logger newLogger) {
        logger = newLogger;
    }

    // Forge uses this to reference this mod. By creating it ourselves instead
    // of having it be generated, we too have a reference to it.
    @Instance
    public static Szeibernaeticks instance = new Szeibernaeticks();

    @SidedProxy(modId = MOD_ID, clientSide = "de.grzb.szeibernaeticks.ClientProxy", serverSide = "de.grzb.szeibernaeticks.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // event.registerServerCommand(new CommandDebug());
    }
}
