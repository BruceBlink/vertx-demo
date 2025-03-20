package com.vertx.eventbus.example.pubsub;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.deployVerticle(new PublisherVerticle());
        vertx.deployVerticle(new Subscriber1Verticle());
        vertx.deployVerticle(new Subscriber2Verticle());
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}
