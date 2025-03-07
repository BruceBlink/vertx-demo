package com.verticle.hello;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class BlockEventLoopVerticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.setTimer(1000, id -> {
            while (true)
                ;
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new BlockEventLoopVerticle());
    }
}
