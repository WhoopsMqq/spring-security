package com.whoops.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)//表示利用SpringRunner来运行这个测试用例
@SpringBootTest             //表明这是一个测试用例
public class UserControllerTest {

    //伪造一个mvc的环境,这样测试的时候就不会去启动tomcat,可以提高测试速度
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before //写了Before的方法会在测试用例执行之前去执行
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception{
        //perform:执行 利用它去发一个请求,然后去判断请求返回来的内容是不是符合我们的一个期望
        //MockMvcRequestBuilders.get(URL):会模拟发出一个get请求
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                //设置contentType,利用UTF-8这种编码格式去发送一个contentType的请求
               .contentType(MediaType.APPLICATION_JSON_UTF8))
                //andExpect():设置对请求结果的期望  这里设置的是期望返回的状态码是200
               .andExpect(MockMvcResultMatchers.status().isOk())
                //设置期望返回的结果的集合长度是3,包含了三个元素  jsonPath():解析返回的json内容,对内容做一个判断
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
               .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenQuerySuccess2() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .param("username","mqq")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

    @Test
    public void whenCreateSuccess() throws Exception{
        String content = "{\"username\":\"whoops\",\"password\":\"moon2003\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(content))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("whoops"));
    }

    @Test
    public void whenUpdateSuccess() throws Exception{
        String content = "{\"username\":\"whoops\",\"password\":\"moon2003\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("whoops"));
    }

    @Test
    public void whenDeleteSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenUploadSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
               .file(new MockMultipartFile("file","text.txt","multipart/form-data","hello world!".getBytes())))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }


}































