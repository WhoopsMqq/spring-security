package com.whoops.async;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //同步处理方式
    @RequestMapping("/order")
    public String order() throws Exception{
        logger.info("主线程开始");
        Thread.sleep(1000);
        logger.info("主线程结束");
        return "success";
    }

    //使用Runnable异步处理Rest服务
    @RequestMapping("/order2")
    public Callable<String> order2() throws Exception{
        logger.info("主线程开始");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始");
                Thread.sleep(1000);
                logger.info("副线程返回");
                return "success";
            }
        };
        logger.info("主线程结束");
        return result;
    }

    //使用DeferredResult异步处理
    @RequestMapping("/order3")
    public DeferredResult<String> order3() throws Exception{
        logger.info("主线程开始");
        //1.请求进来,生成一个随机的订单号
        String orderId = RandomStringUtils.randomNumeric(8);
        //2.放入到消息队列中去
        mockQueue.setPlaceOrder(orderId);
        //3.new 一个DeferredResult对象
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderId,result);

        logger.info("主线程结束");
        return result;
    }


}



























