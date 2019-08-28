package com.whoops.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 */
//Target 表示注解可以标注在什么样的元素上  ElementType.METHOD:方法  ElementType.FIELD:字段
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)//表示运行时注解
@Constraint(validatedBy = MyConstrainImpl.class)//指定一个类去校验他的逻辑
public @interface MyConstrain {
    String message() default "{校验不通过以后的提示}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //上面的三个属性是必须有的







}






















