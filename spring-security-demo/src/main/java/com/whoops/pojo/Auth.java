package com.whoops.pojo;

import org.springframework.security.core.GrantedAuthority;

public class Auth implements GrantedAuthority {

    private int id;

    private String name;

    public Auth() {
    }

    public Auth(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
