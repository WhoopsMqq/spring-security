package com.whoops.async;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            while (true){
                if (StringUtils.isNoneBlank(mockQueue.getCompleteOrder())){
                    String orderId = mockQueue.getCompleteOrder();
                    logger.info("返回处理结果:"+orderId);
                    deferredResultHolder.getMap().get(orderId).setResult("success");
                    mockQueue.setCompleteOrder(null);
                }
            }
        }).start();
    }
}





















