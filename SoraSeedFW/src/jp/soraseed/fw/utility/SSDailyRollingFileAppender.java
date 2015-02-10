package jp.soraseed.fw.utility;

import java.io.IOException;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;

/**
 * log4j拡張クラス
 *
 */
public class SSDailyRollingFileAppender extends DailyRollingFileAppender {
    
    public SSDailyRollingFileAppender(
            final Layout layout, final String filename, final String datePattern)
            throws IOException {
        super(layout, filename, datePattern);
        setEncoding("UTF-8");
    }
    
    public SSDailyRollingFileAppender() {
        super();
        setEncoding("UTF-8");
    }
}
