package com.vertx.eventbus.example.sendreceiver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class ReceiverVerticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.eventBus().consumer("address.message", message -> {
            JsonObject body = (JsonObject) message.body();
            System.out.println("Received message: " + body.encode());
        });
    }
}
