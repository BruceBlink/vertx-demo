package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

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

        // Create the HTTP server
        vertx.createHttpServer()
            // Handle every request using the routerrequestHandler(router)
            // Start listening
            .listen(8888)
            // Print the port on success
            .onSuccess(server -> {
                System.out.println("HTTP server started on port " + server.actualPort());
                startPromise.complete();
            })
            // Print the problem on failure
            .onFailure(throwable -> {
                throwable.printStackTrace();
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
}
