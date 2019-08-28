package com.whoops.core.config;

import com.whoops.core.properties.SecurityProperties;
import com.whoops.core.validate.DefaultSmsCodeSender;
import com.whoops.core.validate.ImageCode;
import com.whoops.core.validate.SmsCode;
import com.whoops.core.validate.SmsCodeSender;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController
public class ValidateCodeController {

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private DefaultSmsCodeSender defaultSmsCodeSender;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public static final String SESSION_IMAGE_KEY = "SESSION_KEY_IMAGE_CODE";

    public static final String SESSION_SMS_KEY = "SESSION_KEY_SMS_CODE";

    @GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException{
        //1.根据随机数生成一个图片
        ImageCode imageCode = createImageCode(new ServletWebRequest(request));
        //2.将随机数保持到session中
        //new ServletWebRequest(request):把请求放进去,SessionStrategy会从这个请求中去获取session
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_IMAGE_KEY,imageCode);
        //3.将生成的图片写到接口的响应中
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        //1.生成一个随机验证码
        SmsCode smsCode = createSmsCode(new ServletWebRequest(request));
        //2.将验证码保持到session中
        //new ServletWebRequest(request):把请求放进去,SessionStrategy会从这个请求中去获取session
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_SMS_KEY+mobile,smsCode);
        //3.将短信验证码发送给用户
        defaultSmsCodeSender.send(mobile,smsCode.getCode());
    }

    private SmsCode createSmsCode(ServletWebRequest request){
        String code = RandomStringUtils.randomNumeric(properties.getCode().getSms().getLength());
        return new SmsCode(code,properties.getCode().getSms().getExpireIn());
    }

    private ImageCode createImageCode(ServletWebRequest request){
        //先从请求中去取这个值,没有则使用配置的参数
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width",properties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(),"height",properties.getCode().getImage().getHeight());
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        Random random = new Random();
        graphics.setColor(getRandColor(200,250));
        graphics.fillRect(0,0,width,height);
        graphics.setFont(new Font("Times New Roman",Font.ITALIC,20));
        graphics.setColor(getRandColor(160,200));
        for(int i = 0;i < 155;i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x,y,x+xl,y+yl);
        }
        // a1(random number,0~10) a2(1:+;0:-) a3(random number,0~10) a4(=) a5(?)
        int a1 = random.nextInt(10);
        int a2 = random.nextInt(1);
        int a3 = random.nextInt(10);
        int result = 0;
        String code = "";
        if(a2 == 1){
            result = a1 + a3;
            code = a1+"+"+a3+"="+"?";
        }else {
            result = a1 - a3;
            code = a1+"-"+a3+"="+"?";
        }
        graphics.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));
        graphics.drawString(code,10,16);
        graphics.dispose();
        int exptreInt = properties.getCode().getImage().getExpireInt();
        return new ImageCode(image,code,exptreInt,result);
    }

    private Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc > 255){
            fc = 255;
        }
        if(bc > 255){
            bc = 255;
        }
        int r = fc + random .nextInt(bc - fc);
        int g = fc + random .nextInt(bc - fc);
        int b = fc + random .nextInt(bc - fc);
        return new Color(r,g,b);
    }

}


















