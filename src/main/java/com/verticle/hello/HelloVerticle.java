package com.verticle.hello;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(HelloVerticle.class);
    private long counter = 1L;

    @Override
    public void start() {
        // 创建一个定时作业
        vertx.setPeriodic(5000, id -> logger.info("tick"));
        // 创建一个http服务器
        vertx.createHttpServer()
            .requestHandler(req -> {
                logger.info("Request #{} from {}", counter++, req.remoteAddress().host());
                req.response().end("Hello!");
            }).listen(8888)
            .onSuccess(server -> logger.info("HTTP server started on port: {}", server.actualPort()))
            // Print the problem on failure
            .onFailure(throwable -> logger.info("HTTP server start failure: {}", throwable.getMessage()));
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        //部署Verticle最简单的方式
        vertx.deployVerticle(new HelloVerticle());
    }
}
