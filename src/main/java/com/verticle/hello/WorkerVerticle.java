package com.verticle.hello;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.ThreadingModel;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(WorkerVerticle.class);

    @Override
    public void start() {
        vertx.setPeriodic(10_000, id -> {

            try {
                logger.info("Zzz...");
                Thread.sleep(8000);
                logger.info("Up");
            } catch (InterruptedException e) {
                logger.error("Wrong", e);
            }
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        // 定义部署配置
        DeploymentOptions opts = new DeploymentOptions()
            //设置2个实例
            .setInstances(2)
            //设置为Worker线程
            .setThreadingModel(ThreadingModel.WORKER);
        vertx.deployVerticle("com.verticle.hello.WorkerVerticle", opts);
    }
}
