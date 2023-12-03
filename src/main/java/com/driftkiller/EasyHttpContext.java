package com.driftkiller;

import com.sun.net.httpserver.*;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class EasyHttpContext extends HttpContext {

    private HttpContext context;


    @Override
    public HttpHandler getHandler() {
        return null;
    }

    @Override
    public void setHandler(HttpHandler h) {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public HttpServer getServer() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public List<Filter> getFilters() {
        return null;
    }

    @Override
    public Authenticator setAuthenticator(Authenticator auth) {
        return null;
    }

    @Override
    public Authenticator getAuthenticator() {
        return null;
    }
}
