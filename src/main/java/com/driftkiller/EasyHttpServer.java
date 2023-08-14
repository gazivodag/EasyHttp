package com.driftkiller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class EasyHttpServer {

    private int port;
    private Class<?>[] routeClasses;
    private HttpServer httpServer;

    public static final Gson GSON = new Gson();

    public EasyHttpServer(int port, Class<?>[] routeClasses) throws IOException {
        this.port = port;
        this.routeClasses = routeClasses;
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        processRoutes();
    }

    private void processRoutes()
    {
        for (Class<?> routeClass : routeClasses)
        {
            try {
                Method[] methods = Arrays.stream(routeClass.getDeclaredMethods())
                        .filter(m -> Modifier.isStatic(m.getModifiers()))
                        .filter(m -> m.isAnnotationPresent(EasyRoute.class))
                        .toArray(Method[]::new);

                for (Method method : methods) {
                    System.out.println("Attempting to register " + method.getDeclaringClass().getName() + "." + method.getName() + "()");
                    EasyRoute route = method.getAnnotation(EasyRoute.class);
                    HttpContext context = httpServer.createContext(route.path());
                    context.setHandler(new HttpMethodReflectionHandler(method));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void start()
    {
        this.httpServer.start();
    }

    public void stop(int delay)
    {
        this.httpServer.stop(delay);
    }

}
