package io.scits.nwclib.exception;

public class WebComponentNameNotAvailableException extends RuntimeException {
    public WebComponentNameNotAvailableException() {
        super();
    }

    public WebComponentNameNotAvailableException(String message) {
        super(message);
    }

    public WebComponentNameNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebComponentNameNotAvailableException(Throwable cause) {
        super(cause);
    }

    protected WebComponentNameNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
