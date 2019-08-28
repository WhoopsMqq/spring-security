package com.whoops.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoops.browser.vo.SimpleResponse;
import com.whoops.core.properties.LoginType;
import com.whoops.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("browserAuthenticationFailureHandler")
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 这个方法在登录失败以后会被调用
     * AuthenticationException:包含了认证过程中发生的错误包含的异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败!");

        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){//判断自定义的返回类型是Json还是直接跳转
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());//返回500状态码
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));//返回错误信息
        }else{
            //SpringSecurity原生的处理方式就是直接跳转
            super.onAuthenticationFailure(request,response,exception);
        }

    }
}
























