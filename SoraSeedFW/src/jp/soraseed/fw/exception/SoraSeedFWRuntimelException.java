package jp.soraseed.fw.exception;

import jp.soraseed.fw.utility.ErrorPrint;

public class SoraSeedFWRuntimelException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1545866699857136484L;

    
    /** エラーコード */
    private int errorCode;
    
    /** エラーメッセージ */
    private String errorMassage;
    
    /** ネストエラー情報 */
    private Exception nestedException;
    
    public SoraSeedFWRuntimelException() {
        super();
    }
    
    public SoraSeedFWRuntimelException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }
    
    public SoraSeedFWRuntimelException(String errorMassage) {
        super();
        this.errorMassage = errorMassage;
    }
    
    public SoraSeedFWRuntimelException(Exception nestedException) {
        super();
        this.nestedException = nestedException;
        ErrorPrint.sysout(nestedException);
    }
    
    public SoraSeedFWRuntimelException(int errorCode, String errorMassage) {
        super();
        this.errorCode = errorCode;
        this.errorMassage = errorMassage;
        ErrorPrint.sysout(errorCode + ":" + errorMassage);
    }

    public SoraSeedFWRuntimelException(int errorCode, Exception nestedException) {
        super();
        this.errorCode = errorCode;
        this.nestedException = nestedException;
        ErrorPrint.sysout(nestedException);
    }
    
    public SoraSeedFWRuntimelException(String errorMassage, Exception nestedException) {
        super();
        this.errorMassage = errorMassage;
        this.nestedException = nestedException;
        ErrorPrint.sysout(nestedException);
    }
    
    public SoraSeedFWRuntimelException(int errorCode, String errorMassage, Exception nestedException) {
        super();
        this.errorCode = errorCode;
        this.errorMassage = errorMassage;
        this.nestedException = nestedException;
        ErrorPrint.sysout(nestedException);
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
