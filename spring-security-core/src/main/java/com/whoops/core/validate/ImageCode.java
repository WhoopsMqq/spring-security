package com.whoops.core.validate;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode {

    private BufferedImage image;

    private String code;//表达式  例如: 3+1=?  3-2=?

    private LocalDateTime expireTime;

    private int result;//表达式的结果

    protected ImageCode() {
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime, int result) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
        this.result = result;
    }

    public ImageCode(BufferedImage image, String code, int expireInt, int result) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireInt);
        this.result = result;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isExpried(){
        if(expireTime.isBefore(LocalDateTime.now())){
            return true;
        }else{
            return false;
        }
    }
}
