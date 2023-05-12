package com.example.campus.service.ex;

public class loginException extends ServiceException{
    public loginException() {
        super();
    }

    public loginException(String message) {
        super(message);
    }

    public loginException(String message, Throwable cause) {
        super(message, cause);
    }

    public loginException(Throwable cause) {
        super(cause);
    }

    protected loginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
