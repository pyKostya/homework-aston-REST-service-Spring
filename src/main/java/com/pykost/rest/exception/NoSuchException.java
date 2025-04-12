package com.pykost.rest.exception;

public class NoSuchException extends RuntimeException {
    public NoSuchException(String message) {
        super(message);
    }
}
