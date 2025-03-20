package com.vertx.eventbus.example.pubsub;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

/**
 * 发布者Verticle
 */
public class PublisherVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.setPeriodic(3000, id -> {
            JsonObject message = new JsonObject().put("temp", 25 + Math.random() * 10);
            vertx.eventBus().publish("publish.update", message);
            System.out.println("Published message: " + message.encode());
        });
    }
}
