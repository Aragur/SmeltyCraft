package net.aragurlp.smeltycraft.util;

import cpw.mods.fml.common.FMLLog;
import net.aragurlp.smeltycraft.reference.Reference;
import org.apache.logging.log4j.Level;

public class LogHelper {

    public static void log(Level logLevel, Object object) {
        FMLLog.log(Reference.MOD_NAME, logLevel, String.valueOf(object));
    }

    /**
     * A severe error that will prevent the application from continuing.
     */
    public static void fatal(Object object) {log(Level.FATAL, object);}

    /**
     * An error in the application, possibly recoverable.
     */
    public static void error(Object object) {log(Level.ERROR, object);}

    /**
     * An event that might possible lead to an error.
     */
    public static void warn(Object object) {log(Level.WARN, object);}

    /**
     * An event for informational purposes.
     */
    public static void info(Object object) {log(Level.INFO, object);}

    /**
     * A general debugging event.
     */
    public static void debug(Object object) {log(Level.DEBUG, object);}

}
