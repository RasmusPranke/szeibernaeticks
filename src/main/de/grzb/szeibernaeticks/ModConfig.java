package main.de.grzb.szeibernaeticks;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Szeibernaeticks.MOD_ID)
public class ModConfig {

    public static final Debug debug = new Debug();
    public static class Debug {
        public final Boolean INFO_ENABLED = true;
        public final Boolean ERROR_ENABLED = true;
        public final Boolean EXCEPTION_ENABLED = true;
        public final Boolean SETUP_ENABLED = true;
        public final Boolean ITEM_ENABLED = false;
        public final Boolean SZEIBER_CAP_ENABLED = false;
        public final Boolean SZEIBER_HANDLER_ENABLED = false;
        public final Boolean SZEIBER_ARM_ENABLED = false;
        public final Boolean SZEIBER_ENERGY_ENABLED = false;
        public final Boolean COMMAND_ENABLED = false;
        public final Boolean SPAMMY_ENABLED = false;
        public final Boolean INSTANTIATION_ENABLED = false;
        public final Boolean SPECIFIC_ENABLED = false;
        public final Boolean DEBUG_ENABLED = false;
        public final Boolean RENDER_ENABLED = false;
    }

    public static final WorldGen worldGen = new WorldGen();
    public static class WorldGen {
        public final Boolean GENERATE_COPPER = true;
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equals(Szeibernaeticks.MOD_ID)) {
            ConfigManager.sync(Szeibernaeticks.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
