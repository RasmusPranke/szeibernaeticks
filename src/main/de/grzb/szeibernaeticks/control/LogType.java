package main.de.grzb.szeibernaeticks.control;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * The types of information one might want to log. Don't make assumptions about
 * which apply, only use those that you are sure of. For example, don't use
 * SETUP unless the method you use it in explicitly is part of the setup
 * process.
 *
 * @author DemRat
 */
public enum LogType {
    /**
     * General Information about the loading and structure of the mod.
     */
    INFO(),
    /**
     * Errors during Mod execution.
     */
    ERROR(),
    /**
     * Information concerning Exceptions. USE
     * {@code Szeibernaeticks.getLogger().exception()} INSTEAD UNLESS YOU HAVE A
     * GOOD REASON.
     */
    EXCEPTION(),
    /**
     * Information concerning the inital loading of the mod.
     */
    SETUP(),
    /**
     * General Information about Items.
     */
    ITEM(),
    /**
     * Information concerning Szeibernaetick Capabilities.
     */
    SZEIBER_CAP(),
    /**
     * Information concerning Szeibernaetick Event Handlers.
     */
    SZEIBER_HANDLER(),
    /**
     * Information concerning Szeiberneatick Armouries.
     */
    SZEIBER_ARM(),
    /**
     * Information concerning Szeiberneatick Energy.
     */
    SZEIBER_ENERGY(),
    /**
     * Information concerning Commands.
     */
    COMMAND(),
    /**
     * Logs that produce output that cannot be prevented by the player, i.e. all
     * logs that produce output indefinitely in a newly generated world without
     * mobs or anything else.
     */
    SPAMMY(),
    /**
     * Information about the inner workings of the mod.
     */
    DEBUG(),
    /**
     * Information concerning the instantiation of classes.
     */
    INSTANTIATION(),
    /**
     * Specific Information. For example, exact values used in a method.
     */
    SPECIFIC(),
    /**
     * Information concerning rendering.
     */
    RENDER(),
    /**
     * Temporary debug messages.
     */
    TEMP();

    LogType() {
    }

    public boolean enabled() {
        switch(this) {
            case COMMAND:
                return DebugConfig.COMMAND_ENABLED;
            case DEBUG:
                return DebugConfig.DEBUG_ENABLED;
            case ERROR:
                return DebugConfig.ERROR_ENABLED;
            case EXCEPTION:
                return DebugConfig.EXCEPTION_ENABLED;
            case INFO:
                return DebugConfig.INFO_ENABLED;
            case INSTANTIATION:
                return DebugConfig.INSTANTIATION_ENABLED;
            case ITEM:
                return DebugConfig.ITEM_ENABLED;
            case RENDER:
                return DebugConfig.RENDER_ENABLED;
            case SETUP:
                return DebugConfig.SETUP_ENABLED;
            case SPAMMY:
                return DebugConfig.SPAMMY_ENABLED;
            case SPECIFIC:
                return DebugConfig.SPECIFIC_ENABLED;
            case SZEIBER_ARM:
                return DebugConfig.SZEIBER_ARM_ENABLED;
            case SZEIBER_CAP:
                return DebugConfig.SZEIBER_CAP_ENABLED;
            case SZEIBER_ENERGY:
                return DebugConfig.SZEIBER_ENERGY_ENABLED;
            case SZEIBER_HANDLER:
                return DebugConfig.SZEIBER_HANDLER_ENABLED;
            case TEMP:
            default:
                return true;
        }
    }

    @Config(modid = Szeibernaeticks.MOD_ID, name = "MessageConfig", type = Type.INSTANCE)
    public static class DebugConfig {
        public static Boolean INFO_ENABLED = true;
        public static Boolean ERROR_ENABLED = true;
        public static Boolean EXCEPTION_ENABLED = true;
        public static Boolean SETUP_ENABLED = true;
        public static Boolean ITEM_ENABLED = false;
        public static Boolean SZEIBER_CAP_ENABLED = false;
        public static Boolean SZEIBER_HANDLER_ENABLED = false;
        public static Boolean SZEIBER_ARM_ENABLED = false;
        public static Boolean SZEIBER_ENERGY_ENABLED = false;
        public static Boolean COMMAND_ENABLED = false;
        public static Boolean SPAMMY_ENABLED = false;
        public static Boolean INSTANTIATION_ENABLED = false;
        public static Boolean SPECIFIC_ENABLED = false;
        public static Boolean DEBUG_ENABLED = false;
        public static Boolean RENDER_ENABLED = false;

        @SubscribeEvent
        public void onConfigChanged(OnConfigChangedEvent event) {
            if(event.getModID().equals(Szeibernaeticks.MOD_ID)) {
                ConfigManager.sync(Szeibernaeticks.MOD_ID, Type.INSTANCE);
            }
        }
    }
}
