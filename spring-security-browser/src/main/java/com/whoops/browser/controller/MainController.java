package com.whoops.browser.controller;

import com.whoops.browser.vo.SimpleResponse;
import com.whoops.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时,跳转到这里
     */
    @RequestMapping("/authentication")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //得到引发跳转的请求
        SavedRequest savedRequest = requestCache.getRequest(request,response);

        if(savedRequest != null){
            String target = savedRequest.getRedirectUrl();//得到对应的url
            logger.info("引发跳转的请求是:"+target);
            if(StringUtils.endsWithIgnoreCase(target,".html")){//判断url是不是以.html结尾
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("需要进行用户认证!");
    }

}
