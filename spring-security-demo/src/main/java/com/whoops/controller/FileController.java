package com.whoops.controller;

import com.whoops.pojo.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
public class FileController {

    private final static String FOLDER = "E:\\idea_code\\spring-security\\spring-security-demo\\src\\main\\resources\\upload";

    @PostMapping("/file")
    public FileInfo upload(MultipartFile file) throws Exception{
        System.out.println(file.getName());//file  传下来的参数的值
        System.out.println(file.getOriginalFilename());//原始的文件名
        System.out.println(file.getSize());//文件大小
        //文件要存放的路径

        File locaFile = new File(FOLDER,file.getOriginalFilename());

        file.transferTo(locaFile);//将传进来的文件写到创建的本地文件中

        return new FileInfo(locaFile.getAbsolutePath());
    }

    @GetMapping("/file/{id}")
    public void download(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response){

        try
        (
            InputStream inputStream =  new FileInputStream(new File(FOLDER,id+".txt"));
            OutputStream outputStream = response.getOutputStream();
        )
        {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename=text.txt");
            IOUtils.copy(inputStream,outputStream);//将inputStream里的内容写到outputStream里去
            outputStream.flush();

        }catch (Exception e){

        }

    }

}






















