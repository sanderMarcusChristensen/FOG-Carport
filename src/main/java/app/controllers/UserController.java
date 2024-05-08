package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;

public class UserController {

    public static void addRoutes(Javalin app) {
        app.get("customCarport", ctx -> ctx.render("customCarport.html"));
        app.get("homepage", ctx -> ctx.render("index.html"));
        app.get("loginPage", ctx -> ctx.render("login.html"));
    }

}
