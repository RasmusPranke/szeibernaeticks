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
    INFO(DebugConfig.INFO_ENABLED),
    /**
     * Errors during Mod execution.
     */
    ERROR(DebugConfig.ERROR_ENABLED),
    /**
     * Information concerning Exceptions. USE
     * {@code Szeibernaeticks.getLogger().exception()} INSTEAD UNLESS YOU HAVE A
     * GOOD REASON.
     */
    EXCEPTION(DebugConfig.EXCEPTION_ENABLED),
    /**
     * Information concerning the inital loading of the mod.
     */
    SETUP(DebugConfig.SETUP_ENABLED),
    /**
     * General Information about Items.
     */
    ITEM(DebugConfig.ITEM_ENABLED),
    /**
     * Information concerning Szeibernaetick Capabilities.
     */
    SZEIBER_CAP(DebugConfig.SZEIBER_CAP_ENABLED),
    /**
     * Information concerning Szeibernaetick Event Handlers.
     */
    SZEIBER_HANDLER(DebugConfig.SZEIBER_HANDLER_ENABLED),
    /**
     * Information concerning Szeiberneatick Armouries.
     */
    SZEIBER_ARM(DebugConfig.SZEIBER_ARM_ENABLED),
    /**
     * Information concerning Szeiberneatick Energy.
     */
    SZEIBER_ENERGY(DebugConfig.SZEIBER_ENERGY_ENABLED),
    /**
     * Information concerning Commands.
     */
    COMMAND(DebugConfig.COMMAND_ENABLED),
    /**
     * Logs that produce output that cannot be prevented by the player, i.e. all
     * logs that produce output indefinitely in a newly generated world without
     * mobs or anything else.
     */
    SPAMMY(DebugConfig.SPAMMY_ENABLED),
    /**
     * Information about the inner workings of the mod.
     */
    DEBUG(DebugConfig.DEBUG_ENABLED),
    /**
     * Information concerning the instantiation of classes.
     */
    INSTANTIATION(DebugConfig.INSTANTIATION_ENABLED),
    /**
     * Specific Information. For example, exact values used in a method.
     */
    SPECIFIC(DebugConfig.SPECIFIC_ENABLED),
    /**
     * Information concerning rendering.
     */
    RENDER(DebugConfig.RENDER_ENABLED),
    /**
     * Temporary debug messages.
     */
    TEMP(new Boolean(true));

    private Boolean isEnabled;

    LogType(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean enabled() {
        return isEnabled;
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
