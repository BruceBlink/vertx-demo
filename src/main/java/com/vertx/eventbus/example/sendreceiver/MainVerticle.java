package com.vertx.eventbus.example.sendreceiver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.deployVerticle(new SenderVerticle());
        vertx.deployVerticle(new ReceiverVerticle());
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}

