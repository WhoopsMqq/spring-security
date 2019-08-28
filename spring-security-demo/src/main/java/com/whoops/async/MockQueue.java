package com.whoops.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockQueue {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String placeOrder;    //表示消息队列接收到请求的消息

    private String completeOrder; //表示应用2处理完请求返回的结果

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder){
        new Thread(() -> {
            logger.info("接到下单的请求:"+placeOrder);
            this.placeOrder = placeOrder;
            this.completeOrder = placeOrder;
            logger.info("应用2处理完毕返回结果:"+completeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
