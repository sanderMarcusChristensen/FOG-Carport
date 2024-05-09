package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class OrderItemController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.post("continueToPageTwo", ctx -> customCarport_pagetwo(ctx));
        app.get("continueToPageThree", ctx -> ctx.render("customCarport_PageThree.html"));
        app.get("backToPageOne", ctx -> customCarport_pageone(ctx));
    }

    private static void customCarport_pageone(Context ctx) {
        ctx.render("customCarport.html");
    }

    private static void customCarport_pagetwo(Context ctx) {

        int carportWidth = Integer.parseInt(ctx.formParam("width_id"));
        int carportLength = Integer.parseInt(ctx.formParam("length_id"));
        String carportRoof = ctx.formParam("roof_id");

        ctx.sessionAttribute("carportWidth", carportWidth);
        ctx.sessionAttribute("carportLength", carportLength);

        ctx.render("customCarport_pageTwo.html");
    }
}
