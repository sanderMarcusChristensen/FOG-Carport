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
    private static final String URL = "jdbc:postgresql://209.38.202.233:5432/%s?currentSchema=public";
    private static final String DB = "carport_jon";



    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);  //initialisere vores Conncetion med instansvariabler

    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {  //Laver en javalin server
            config.staticFiles.add("/public");  //Fortæller hvor vi kan find vores "statiske filer"

            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig())); //initialisere seassionConig

            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine())); //initialisere thymaleaf

        }).start(7070);//Starter javalin på port 7070

        // Routing

        //app.get håndtere alle anmodninger og viser forside ved hjælp af ctx (Context)
        app.get("/", ctx -> ctx.render("index.html"));

        //Tilføjer forskellige "routes" så man kan komme rundt på siden
        UserController.addRoutes(app, connectionPool);
        OrderController.addRoutes(app, connectionPool);
        AdminController.addRoutes(app, connectionPool);
    }
}
