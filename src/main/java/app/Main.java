package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.AdminController;
import app.controllers.OrderController;
import app.controllers.UserController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "Teknologisk2023!";
    private static final String URL = "jdbc:postgresql://db:5432/%s?currentSchema=public";
    private static final String DB = "carport_jon";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing

        app.get("/", ctx -> ctx.render("index.html"));
        UserController.addRoutes(app, connectionPool);
        OrderController.addRoutes(app, connectionPool);
        AdminController.addRoutes(app, connectionPool);
    }
}
