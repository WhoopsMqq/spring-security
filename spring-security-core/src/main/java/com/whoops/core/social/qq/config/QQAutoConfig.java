package com.whoops.core.social.qq.config;

import com.whoops.core.properties.QQProperties;
import com.whoops.core.properties.SecurityProperties;
import com.whoops.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

@Configuration
//只有当 配置文件中 whoops.security.social.qq.app-id 有值了 这里的配置才会生效
@ConditionalOnProperty(prefix = "whoops.security.social.qq",name = "appId")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(),qqProperties.getAppId(),qqProperties.getAppSecret());
    }
}
