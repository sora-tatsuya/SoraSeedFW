package jp.soraseed.fw.utility;

import org.apache.log4j.Logger;


/**
 * フレームワークログ出力
 * @author Tatsuya
 *
 */
public final class LogUtilities {

    protected static Logger debugLoggr = Logger.getLogger("fwDebug");
    protected static Logger infoLoggr = Logger.getLogger("fwInfo");
    protected static Logger errorLoggr = Logger.getLogger("fwError");
    
    private LogUtilities(){}
    
    public static void debug(Object obj) {
        debugLoggr.debug(obj);
    }
    public static void info(Object obj) {
        infoLoggr.info(obj);
    }
    public static void error(Object obj) {
        errorLoggr.error(obj);
    }
}
