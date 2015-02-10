package jp.soraseed.fw.exception;

public class SoraSeedFWException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8050043234276978675L;

    /** エラーコード */
    private int errorCode;
    
    /** エラーメッセージ */
    private String errorMassage;
    
    /** ネストエラー情報 */
    private Exception nestedException;
    
    public SoraSeedFWException() {
        super();
    }
    
    public SoraSeedFWException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }
    
    public SoraSeedFWException(String errorMassage) {
        super();
        this.errorMassage = errorMassage;
    }
    
    public SoraSeedFWException(Exception nestedException) {
        super();
        this.nestedException = nestedException;
    }
    
    public SoraSeedFWException(int errorCode, String errorMassage) {
        super();
        this.errorCode = errorCode;
        this.errorMassage = errorMassage;
    }

    public SoraSeedFWException(int errorCode, Exception nestedException) {
        super();
        this.errorCode = errorCode;
        this.nestedException = nestedException;
    }
    
    public SoraSeedFWException(String errorMassage, Exception nestedException) {
        super();
        this.errorMassage = errorMassage;
        this.nestedException = nestedException;
    }
    
    public SoraSeedFWException(int errorCode, String errorMassage, Exception nestedException) {
        super();
        this.errorCode = errorCode;
        this.errorMassage = errorMassage;
        this.nestedException = nestedException;
    }
    

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMassage() {
        return errorMassage;
    }

    public void setErrorMassage(String errorMassage) {
        this.errorMassage = errorMassage;
    }

    public Exception getNestedException() {
        return nestedException;
    }

    public void setNestedException(Exception nestedException) {
        this.nestedException = nestedException;
    }
    
    
}
