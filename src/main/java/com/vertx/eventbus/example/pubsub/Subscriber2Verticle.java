package com.vertx.eventbus.example.pubsub;

import io.vertx.core.AbstractVerticle;

public class Subscriber2Verticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.eventBus().consumer("publish.update", message -> System.out.println("Subscriber 2 received: " + message.body()));
    }
}
