package com.whoops.browser.config;

import com.whoops.browser.authentication.BrowserAuthenticationFailureHandler;
import com.whoops.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.whoops.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.whoops.core.properties.SecurityProperties;
import com.whoops.core.validate.ImageCodeFilter;
import com.whoops.core.validate.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

//WebSecurityConfigurerAdapter:springsecurity提供的适配器类,
// 专门用来做web应用安全配置的适配器

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private BrowserAuthenticationSuccessHandler browserAuthenticationSuccessHandler;

    @Autowired
    private BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private DataSource dataSource;  //这个datasource就是在yml里面配置好的

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SpringSocialConfigurer socialSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ImageCodeFilter imageCodeFilter = new ImageCodeFilter();
        imageCodeFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);
        imageCodeFilter.setProperties(securityProperties);
        imageCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);
        smsCodeFilter.setProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

             //将validateCodeFilter加到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class)
            .formLogin()//表单登录
                .loginPage("/authentication")
                .loginProcessingUrl("/login-in")
                .successHandler(browserAuthenticationSuccessHandler)
                .failureHandler(browserAuthenticationFailureHandler)
                .failureUrl(securityProperties.getBrowser().getLoginPage())
            .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
            .and().authorizeRequests()//对请求授权
            .antMatchers("/authentication",
                    securityProperties.getBrowser().getLoginPage(),
                    "/code/*").permitAll()
            .anyRequest()//任何请求
            .authenticated()//都需要身份认证
            .and()
            .csrf().disable()
            //将SmsCodeAuthenticationSecurityConfig中写的配置添加进来
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            .apply(socialSecurityConfig);

    }
}
