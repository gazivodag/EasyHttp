package com.driftkiller;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that is used to mark a method for EasyHttp integration.
 * By annotating a method with this annotation, the method can be hooked onto an HttpContext
 * provided by the EasyHttp library via reflection (using LambdaMetaFactory).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EasyRoute {
    /**
     * Specifies the route or URL path associated with the annotated method.
     *
     * @return The route or URL path. Default value is "/".
     */
    String path() default "/";

    /**
     * Specifies the HTTP method type used by the annotated method.
     *
     * @return The HTTP method type. Default value is HttpMethodType.GET.
     */
    HttpMethodType httpMethodType() default HttpMethodType.GET;
}
