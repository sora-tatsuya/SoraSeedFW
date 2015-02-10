package jp.soraseed.fw.utility;

/**
 * 
 * @author Tatsuya
 *
 */
public class ErrorPrint {

    private ErrorPrint(){}
    
    public static void sysout(Exception e) {
        if (Constants.STATUS == 0) {
            e.printStackTrace();
        }
    }
    public static void sysout(String str) {
        if (Constants.STATUS == 0) {
            System.out.println(str);
        }
    }
}
