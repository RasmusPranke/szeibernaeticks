package main.de.grzb.szeibernaeticks.control;

import main.de.grzb.szeibernaeticks.ModConfig;

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
    INFO(ModConfig.debug.INFO_ENABLED),
    /**
     * Errors during Mod execution.
     */
    ERROR(ModConfig.debug.ERROR_ENABLED),
    /**
     * Information concerning Exceptions. USE
     * {@code Szeibernaeticks.getLogger().exception()} INSTEAD UNLESS YOU HAVE A
     * GOOD REASON.
     */
    EXCEPTION(ModConfig.debug.EXCEPTION_ENABLED),
    /**
     * Information concerning the inital loading of the mod.
     */
    SETUP(ModConfig.debug.SETUP_ENABLED),
    /**
     * General Information about Items.
     */
    ITEM(ModConfig.debug.ITEM_ENABLED),
    /**
     * Information concerning Szeibernaetick Capabilities.
     */
    SZEIBER_CAP(ModConfig.debug.SZEIBER_CAP_ENABLED),
    /**
     * Information concerning Szeibernaetick Event Handlers.
     */
    SZEIBER_HANDLER(ModConfig.debug.SZEIBER_HANDLER_ENABLED),
    /**
     * Information concerning Szeiberneatick Armouries.
     */
    SZEIBER_ARM(ModConfig.debug.SZEIBER_ARM_ENABLED),
    /**
     * Information concerning Szeiberneatick Energy.
     */
    SZEIBER_ENERGY(ModConfig.debug.SZEIBER_ENERGY_ENABLED),
    /**
     * Information concerning Commands.
     */
    COMMAND(ModConfig.debug.COMMAND_ENABLED),
    /**
     * Logs that produce output that cannot be prevented by the player, i.e. all
     * logs that produce output indefinitely in a newly generated world without
     * mobs or anything else.
     */
    SPAMMY(ModConfig.debug.SPAMMY_ENABLED),
    /**
     * Information about the inner workings of the mod.
     */
    DEBUG(ModConfig.debug.DEBUG_ENABLED),
    /**
     * Information concerning the instantiation of classes.
     */
    INSTANTIATION(ModConfig.debug.INSTANTIATION_ENABLED),
    /**
     * Specific Information. For example, exact values used in a method.
     */
    SPECIFIC(ModConfig.debug.SPECIFIC_ENABLED),
    /**
     * Information concerning rendering.
     */
    RENDER(ModConfig.debug.RENDER_ENABLED),
    /**
     * Temporary debug messages.
     */
    TEMP(Boolean.TRUE);

    private Boolean isEnabled;

    LogType(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean enabled() {
        return isEnabled;
    }
}
