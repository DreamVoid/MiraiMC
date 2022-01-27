package me.dreamvoid.miraimc.internal.httpapi.exception;

public class AbnormalStatusException extends Exception{
    private final int code;

    public AbnormalStatusException(int code) {
        super();
        this.code = code;
    }

    public AbnormalStatusException(int code, String message) {
        super(message);
        this.code = code;
    }

    public AbnormalStatusException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public AbnormalStatusException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    protected AbnormalStatusException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
