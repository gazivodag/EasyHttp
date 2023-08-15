package com.driftkiller;

import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.fileupload.RequestContext;

import java.io.IOException;
import java.io.InputStream;

class HttpExchangeRequestContext implements RequestContext {
    private final HttpExchange exchange;

    public HttpExchangeRequestContext(HttpExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getCharacterEncoding() {
        return exchange.getRequestHeaders().getFirst("Content-Type").split(";")[1].trim().split("=")[1];
    }

    @Override
    public String getContentType() {
        return exchange.getRequestHeaders().getFirst("Content-Type");
    }

    @Override
    public int getContentLength() {
        return Integer.parseInt(exchange.getRequestHeaders().getFirst("Content-Length"));
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return exchange.getRequestBody();
    }
}