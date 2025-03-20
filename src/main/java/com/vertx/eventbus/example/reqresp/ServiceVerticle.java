package com.vertx.eventbus.example.reqresp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class ServiceVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.eventBus().consumer("service.request", message -> {
            JsonObject request = (JsonObject) message.body();
            System.out.println("Received request: " + request.encode());

            JsonObject response = new JsonObject()
                .put("status", "success")
                .put("message", "Processed: " + request.getString("data"));

            message.reply(response);
        });
    }
}
