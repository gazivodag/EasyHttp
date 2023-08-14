package com.driftkiller;

@FunctionalInterface
interface HttpExchangeMethod {
    void run(EasyHttpInteraction exchange);
}
