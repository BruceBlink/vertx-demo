package com.vertx.eventbus.example.reqresp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class ClientVerticle extends AbstractVerticle {

    @Override
    public void start() {
        JsonObject request = new JsonObject().put("data", "Hello Service!");
        vertx.eventBus().request("service.request", request, reply -> {
            if (reply.succeeded()) {
                System.out.println("Response received: " + reply.result().body());
            } else {
                System.out.println("Failed to receive response");
            }
        });
    }
}
