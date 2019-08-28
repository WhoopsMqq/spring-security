package com.whoops.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

public class QQProperties extends SocialProperties {

    private String providerId = "qq";//服务提供商标识

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
