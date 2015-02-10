package TestBase;

public class AA {

    public static void main(String[] args) throws Exception {
        String[] aa = new String[4];
        Object t = new Object[3];
        String aa1 = new String();
        aa = (String[])t;
        System.out.println(aa);
        getC(aa);
    }
    
    public static <T> T getC(T aa) throws Exception {
        Object obj = aa.getClass().newInstance();
        return (T)obj;
    }
}
