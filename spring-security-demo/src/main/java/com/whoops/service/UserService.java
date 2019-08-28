package com.whoops.service;

import com.whoops.pojo.Auth;
import com.whoops.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 表单登录,根据username去获取User对象
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return createUser(username);
    }

    /**
     * 第三方登录,根据对应的userId去获取User对象
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return createUser(userId);
    }

    private User createUser(String usernameOrId){
        List<Auth> authList = new ArrayList<>();
        authList.add(new Auth("ADMIN"));
        User user = new User(usernameOrId,passwordEncoder.encode("123456"),authList);
        return user;
    }
}
