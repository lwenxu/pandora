package com.ibeetl.admin.console.exception;

import lombok.Data;

/**
 * Created by lwen on 2018/9/19
 */
@Data
public class BaseException extends Exception{
    public BaseException(String message) {
        super(message);
    }

    public BaseException() {
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
