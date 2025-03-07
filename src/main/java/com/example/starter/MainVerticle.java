package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(MainVerticle.class);
    static JsonArray userJsonArray = new JsonArray();

    static {

        userJsonArray.add(new JsonObject().put("name", "Alice").put("age", 30))
            .add(new JsonObject().put("name", "Bob").put("age", 25))
            .add(new JsonObject().put("name", "Charlie").put("age", 35));
    }

    @Override
    public void start(Promise<Void> startPromise) {
        // Create a Router
        Router router = Router.router(vertx);

        // Define a handler for the root path
        router.route("/").handler(this::handleRoot);

        // Define a handler for the /hello path
        router.route("/hello").handler(this::handleHello);

        // Define a handler for the /goodbye path
        router.route("/goodbye").handler(this::handleGoodbye);

        //test GET request
        router.get("/user").handler(this::testGet);
        //test GET request
        router.get("/userList").handler(this::testGetList);
        // 添加BodyHandler以处理请求体
        router.route().handler(BodyHandler.create());
        //test POST request
        router.post("/user").handler(this::testPost);
        //test PUT request
        router.put("/user").handler(this::testPut);
        // test delete request
        router.delete("/user").handler(this::testDelete);

        // Create the HTTP server
        vertx.createHttpServer()
            // Handle every request using the routerrequestHandler(router)
            .requestHandler(router)
            // Start listening
            .listen(8888)
            // Print the port on success
            .onSuccess(server -> {
                logger.info("HTTP server started on port: {}", server.actualPort());
                startPromise.complete();
            })
            // Print the problem on failure
            .onFailure(throwable -> {
                throwable.printStackTrace();
                logger.info("HTTP server start failure: {}", throwable.getMessage());
                startPromise.fail(throwable);
            });
    }

    private void handleRoot(RoutingContext context) {
        context.response()
            .putHeader("content-type", "text/plain")
            .end("Welcome to the API!");
    }

    private void handleHello(RoutingContext context) {
        MultiMap queryParams = context.queryParams();
        String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
        context.json(
            new JsonObject()
                .put("message", "Hello " + name)
        );
    }

    private void handleGoodbye(RoutingContext context) {
        context.json(
            new JsonObject()
                .put("message", "Goodbye!")
        );
    }


    private void testGetList(RoutingContext context) {
        context.json(userJsonArray);
    }

    private void testGet(RoutingContext context) {
        MultiMap queryParams = context.queryParams();
        String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
        JsonObject result = userJsonArray.stream()
            .map(obj -> (JsonObject) obj)
            .filter(jsonObject -> name.equals(jsonObject.getString("name")))
            .findFirst()
            .orElse(null);
        context.json(result);
    }


    private void testPost(RoutingContext context) {
        /**获取地址栏参数
         MultiMap queryParams = context.queryParams();
         String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
         String age = queryParams.contains("age") ? queryParams.get("age") : "unknown";
         **/
        /** 请求体是json字符串
         JsonObject requestBodyJson = context.body().asJsonObject();
         String name = requestBodyJson.getString("name");
         String age = requestBodyJson.getString("age");
         */
        /** 请求体为form表单
         */
        String name = context.request().getFormAttribute("name");
        String age = context.request().getFormAttribute("age");
        // 查找是否已经存在
        boolean isExist = userJsonArray.stream().map(obj -> (JsonObject) obj)
            .anyMatch(jsonObject -> name.equals(jsonObject.getString("name")));
        //如果不存在
        if (!isExist) {
            userJsonArray.add(new JsonObject().put("name", name).put("age", age));
            context.response()
                .putHeader("content-type", "text/plain")
                .end("新增成功!");
        } else {
            context.response()
                .putHeader("content-type", "text/plain")
                .end(name + "已经存在!");
        }

    }

    private void testPut(RoutingContext context) {
        String name = context.request().getFormAttribute("name");
        String age = context.request().getFormAttribute("age");
        JsonObject updatedObject = new JsonObject().put("name", name).put("age", age);
        userJsonArray.stream()
            .map(obj -> (JsonObject) obj)
            .filter(jsonObject -> name.equals(jsonObject.getString("name")))
            .findFirst()
            .ifPresent(jsonObject -> userJsonArray.set(userJsonArray.indexOf(jsonObject), updatedObject));
        context.response()
            .putHeader("content-type", "text/plain")
            .end("更新成功!");

    }

    private void testDelete(RoutingContext context) {
        MultiMap queryParams = context.queryParams();
        String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";

        userJsonArray.stream()
            .map(obj -> (JsonObject) obj)
            .filter(jsonObject -> name.equals(jsonObject.getString("name")))
            .findFirst()
            .ifPresent(jsonObject -> userJsonArray.remove(jsonObject));
        context.response()
            .putHeader("content-type", "text/plain")
            .end("删除成功!");

    }
}
