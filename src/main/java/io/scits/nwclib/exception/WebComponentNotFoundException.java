package io.scits.nwclib.exception;

public class WebComponentNotFoundException extends RuntimeException {
    public WebComponentNotFoundException() {
        super();
    }

    public WebComponentNotFoundException(String message) {
        super(message);
    }

    public WebComponentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebComponentNotFoundException(Throwable cause) {
        super(cause);
    }

    protected WebComponentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
