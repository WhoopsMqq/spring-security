package com.whoops.browser.vo;

public class SimpleResponse {

    private Object message;

    public SimpleResponse(Object message) {
        this.message = message;
    }

    public SimpleResponse() {
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
