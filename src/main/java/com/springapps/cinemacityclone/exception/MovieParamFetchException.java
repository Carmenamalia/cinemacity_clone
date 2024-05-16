package com.springapps.cinemacityclone.exception;

public class MovieParamFetchException extends RuntimeException {

    public MovieParamFetchException(String message) {
        super(message);
    }

    public MovieParamFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
