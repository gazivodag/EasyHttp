package com.driftkiller;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EasyRoute {
    String path() default "/";
    HttpMethodType httpMethodType() default HttpMethodType.GET;
}
