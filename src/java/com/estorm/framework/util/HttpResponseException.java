package com.estorm.framework.util;

public class HttpResponseException extends RuntimeException {

    public HttpResponseException(int responseCode) {
        super("Response Error! err code by: " + responseCode);
    }

}
