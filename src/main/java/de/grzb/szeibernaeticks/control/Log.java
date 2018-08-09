package de.grzb.szeibernaeticks.control;

import org.apache.logging.log4j.Logger;

public class Log {

    private static final Log logger = new Log();

    /**
     * Logs the given message if all given types are activated.
     *
     * @param message
     * @param types
     */
    public static void log(String message, LogType... types) {
        logger.doLog(message, types);
    }

    public static void log(int message, LogType... types) {
        log(Integer.toString(message), types);
    }

    public static Log getLogger() {
        return logger;
    }

    private Logger forgeLogger;

    /**
     * Logs the given message if all given types are enabled.
     *
     * @param message
     * @param types
     */
    private void doLog(String message, LogType... types) {
        boolean log = true;
        boolean isError = false;

        try {
            for(LogType t : types) {
                log = log && t.enabled();
                if(t == LogType.ERROR) {
                    isError = true;
                }
            }
        }
        catch(Exception e) {
            this.forgeLogger.info(e.getMessage());
        }
        if(log) {
            if(!isError) {
                this.forgeLogger.info(message);
            }
            else {
                this.forgeLogger.error(message);
            }
        }
    }

    /*
     * Enables the given LogTypes.
     *
     * @param types public void enable(LogType... types) { for(LogType t :
     * types) { this.typeMap.put(t, Boolean.TRUE); } }
     */

    /*
     * Disables the given LogTypes.
     *
     * @param types public void disable(LogType... types) { for(LogType t :
     * types) { this.typeMap.put(t, Boolean.FALSE); } }
     */

    /**
     * Called once during PreInit to set the logger.
     *
     * @param logger
     */
    public void setForgeLogger(Logger logger) {
        this.forgeLogger = logger;
    }

    /**
     * Use this to log Exceptions themselves.
     *
     * @param t
     */
    public static void logThrowable(Throwable t) {
        logger.forgeLogger.catching(t);
    }
}
