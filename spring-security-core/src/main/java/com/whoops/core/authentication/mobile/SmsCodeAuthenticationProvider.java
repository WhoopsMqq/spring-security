package com.whoops.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 进行身份认证的逻辑
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken)authentication;
        //authenticationToken.getPrincipal()拿到的就是前面传进来的手机号,根据手机号去读取用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername((String)authenticationToken.getPrincipal());

        if(userDetails == null){
            throw new InternalAuthenticationServiceException("不存在对应的账户!");
        }

        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    /**
     * 根据support的返回类型AuthenticationManager选取对于的Provide
     */
    @Override
    public boolean supports(Class<?> authentication) {
        //判断传进来的authentication是不是SmsCodeAuthenticationToken类型的
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
