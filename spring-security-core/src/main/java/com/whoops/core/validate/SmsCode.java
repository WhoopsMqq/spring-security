package com.whoops.core.validate;

import java.time.LocalDateTime;

public class SmsCode {

    private String code;//表达式  例如: 3+1=?  3-2=?

    private LocalDateTime expireTime;

    protected SmsCode() {
    }

    public SmsCode( String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public SmsCode(String code, int expireInt) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireInt);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isExpried(){
        if(expireTime.isBefore(LocalDateTime.now())){
            return true;
        }else{
            return false;
        }
    }
}
