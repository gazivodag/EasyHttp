package com.driftkiller;

@FunctionalInterface
public interface HttpExchangeMethod {
    void run(EasyHttpInteraction interaction);
}
