package com.whoops.core.validate;

import org.springframework.stereotype.Component;

public interface SmsCodeSender {

    void send(String mobile,String code);
}
