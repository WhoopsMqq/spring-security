package com.whoops.core.properties;

public class ImageCodeProperties {

    private int width = 67;
    private int height = 23;
    private int expireInt = 60;

    private String url = "/login-in";

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getExpireInt() {
        return expireInt;
    }

    public void setExpireInt(int expireInt) {
        this.expireInt = expireInt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
