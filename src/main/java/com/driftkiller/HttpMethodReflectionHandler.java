package com.driftkiller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.SneakyThrows;

import java.io.IOException;
import java.lang.invoke.*;
import java.lang.reflect.Method;

class HttpMethodReflectionHandler implements HttpHandler {
    private Method methodRoute;
    private HttpExchangeMethod lambda = null;

    public HttpMethodReflectionHandler(Method methodRoute) {
        this.methodRoute = methodRoute;
        createLambda();
    }
    private EasyRoute getEasyRoute()
    {
        return methodRoute.getAnnotation(EasyRoute.class);
    }

    @SneakyThrows
    private void createLambda() {
        // Create a MethodHandles.Lookup object
        MethodHandles.Lookup caller = MethodHandles.lookup();

        // Get a MethodHandle for the static method
        MethodHandle unreflected = caller.unreflect(methodRoute);

        // Create the CallSite using LambdaMetafactory
        CallSite site = LambdaMetafactory.metafactory(
                caller,
                "run",
                MethodType.methodType(HttpExchangeMethod.class),
                MethodType.methodType(void.class, EasyHttpInteraction.class),
                unreflected,
                MethodType.methodType(void.class, EasyHttpInteraction.class)
        );

        // Get the target lambda function from the CallSite
        this.lambda = (HttpExchangeMethod) site.getTarget().invokeExact();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpMethodType exchangeMethodType = HttpMethodType.valueOf(httpExchange.getRequestMethod());
        HttpMethodType targetMethodType = getEasyRoute().httpMethodType();
        if (exchangeMethodType == targetMethodType)
            lambda.run(new EasyHttpInteraction(httpExchange));
    }

}
