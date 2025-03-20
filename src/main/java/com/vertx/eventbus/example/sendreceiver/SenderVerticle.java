package com.vertx.eventbus.example.sendreceiver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class SenderVerticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.setPeriodic(2000, id -> {
            JsonObject message = new JsonObject().put("content", "Hello from Sender!");
            vertx.eventBus().send("address.message", message);
            System.out.println("Sent message: " + message.encode());
        });
    }
}
