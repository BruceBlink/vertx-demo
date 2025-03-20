package com.vertx.eventbus.example.reqresp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.deployVerticle(new ServiceVerticle());
        vertx.deployVerticle(new ClientVerticle());
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}
