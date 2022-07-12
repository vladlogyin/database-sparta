package over.achievers.database.model;

import org.apache.logging.log4j.LogManager;

public class Logger {
    private final static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger("");

    public static void debug(String msg){
        LOGGER.debug(msg);
    }
    public static void trace(String msg){LOGGER.trace(msg);}
    public static void info(String msg){LOGGER.info(msg);}
    public static void warn(String msg){LOGGER.warn(msg);}
    public static void error(String msg){LOGGER.error(msg);}
    public static void fatal(String msg){LOGGER.fatal(msg);}
}
