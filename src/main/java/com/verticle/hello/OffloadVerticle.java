package com.verticle.hello;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OffloadVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(OffloadVerticle.class);

    @Override
    public void start() {
        vertx.setPeriodic(5000, id -> {
            logger.info("Tick");
            vertx.executeBlocking(this::blockingCode, this::resultHandler);
        });
    }

    private void blockingCode(Promise<String> promise) {
        try {
            logger.info("Blocking code running");
            Thread.sleep(4000);
            logger.info("Done!");
            promise.complete("Ok!");
        } catch (InterruptedException e) {
            promise.fail(e);
        }

    }

    private void resultHandler(AsyncResult<String> asyncResult) {
        if (asyncResult.succeeded()) {
            logger.info("Blocking code result: {}", asyncResult.result());
        } else {
            logger.error("Wrong", asyncResult.cause());
        }
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new OffloadVerticle());
    }
}
