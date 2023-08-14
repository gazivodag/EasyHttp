package com.driftkiller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpMethodType {
    GET("GET"),
    POST("POST"),
    ;

    private final String methodType;
}
