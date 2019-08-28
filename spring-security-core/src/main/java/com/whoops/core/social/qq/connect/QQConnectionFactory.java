package com.whoops.core.social.qq.connect;

import com.whoops.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * @param providerId : 提供商的唯一标识,即qq或者weixin的为一标识
     * @param serviceProvider : 自己实现的服务器提供商
     * @param apiAdapter : 自己写的api适配器
     */
    public QQConnectionFactory(String providerId,String appId,String appSecret ) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
