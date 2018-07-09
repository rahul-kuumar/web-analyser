package com.ms.challenge.error;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

    public BadRequestException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

}
