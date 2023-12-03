package com.driftkiller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EasyHttpServer {

    private int port;
    private Class<?>[] routeClasses;
    private HttpServer httpServer;

    public static final Gson GSON = new Gson();

    //currently EasyHttp needs to be in a fat jar (relative to the other project thats using EasyHttp)
    //it cant be used via java -cp jar1.jar easyhttp.jar because:
    //when jar1 tries to use easyhttp
    public static ClassLoader getClassLoader()
    {
        return EasyHttpServer.class.getClassLoader();
    }
    public EasyHttpServer(int port, Class<?>[] routeClasses) throws IOException {
        this.port = port;
        this.routeClasses = routeClasses;
        this.httpServer = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        processRoutes();
    }

    private void processRoutes()
    {
        List<EasyRoute> easyRoutes = new ArrayList<>();

        for (Class<?> routeClass : routeClasses)
        {
            try {
                Method[] methods = Arrays.stream(routeClass.getDeclaredMethods())
                        .filter(m -> Modifier.isStatic(m.getModifiers()))
                        .filter(m -> m.isAnnotationPresent(EasyRoute.class))
                        .filter(m -> m.getParameterCount() == 1)
                        .filter(m -> m.getParameterTypes()[0] == EasyHttpInteraction.class)
                        .toArray(Method[]::new);

                for (Method method : methods) {
                    System.out.println("Attempting to register " + method.getDeclaringClass().getName() + "." + method.getName() + "()");
                    EasyRoute easyRoute = method.getAnnotation(EasyRoute.class);

                    for (EasyRoute preexistingRoute : easyRoutes)
                        if (preexistingRoute.path().equals(easyRoute.path()) && preexistingRoute.httpMethodType().equals(easyRoute.httpMethodType()))
                            throw new DuplicateEasyRouteException("Route \"" + easyRoute.path() + "\" has already been defined.");

                    try
                    {
                        System.out.println("Attempting to create context & reflection handler for method " + method.getName() + " on easyroutepath " + easyRoute.path() + " and httpmethodtype " + easyRoute.httpMethodType());
                        HttpContext context = httpServer.createContext(easyRoute.path());
                        easyRoutes.add(easyRoute);
                        context.setHandler(new HttpMethodReflectionHandler(method));
                    } catch (Throwable e)
                    {
                        e.printStackTrace();
                    }

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
