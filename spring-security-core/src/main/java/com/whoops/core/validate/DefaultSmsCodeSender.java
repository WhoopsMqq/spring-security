package com.whoops.core.validate;

import org.springframework.stereotype.Component;

@Component
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println(mobile+":"+code);
    }
}
