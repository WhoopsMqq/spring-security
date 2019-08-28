package com.whoops.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.whoops.pojo.Auth;
import com.whoops.pojo.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(value = "username",required = false)String username){
        System.out.println("运行getUser方法");
        List<User> userList = new ArrayList<>();
        List<Auth> authList = new ArrayList<>();
        authList.add(new Auth("ADMIN"));
        if(username != null){
            userList.add(new User(username,"123456"));
            return userList;
        }
        userList.add(new User("whoops1","123456",authList));
        userList.add(new User("whoops2","123456",authList));
        userList.add(new User("whoops3","123456",authList));
        userList.stream().forEach(user -> user.setPassword(user.getUsername()+"123"));
        return userList;
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user){
        return user;
    }

    @PutMapping("/users/{id}")
    public User editUser(@PathVariable(value = "id")String id,@RequestBody User user){
        System.out.println(id);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void delUser(@PathVariable(value = "id")String id){
        System.out.println("id为"+id+"的用户已删除!");
    }

}
