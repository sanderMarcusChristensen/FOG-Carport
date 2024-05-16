package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.lang.module.Configuration;
import java.util.List;

public class AdminController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("adminPage", ctx -> loadAdminPage(ctx, connectionPool));
        app.post("deleteOrder", ctx -> deleteOrder(ctx, connectionPool));
    }



    private static void deleteOrder(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        List<Order> orderAndUserList = ctx.sessionAttribute("orderAndUserList");

        int orderId = Integer.parseInt(ctx.formParam("orderId"));

        try {
            OrderMapper.deleteOrderWithItems(orderId, connectionPool);
            ctx.attribute("orderAndUserList", orderAndUserList);
            ctx.render("adminPage.html");

        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
        }
    }

    private static void loadAdminPage(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Order> orderAndUserList = OrderMapper.getAllOrders(connectionPool);

            ctx.sessionAttribute("orderAndUserList", orderAndUserList);
            ctx.attribute("orderAndUserList", orderAndUserList);

            ctx.render("adminPage.html");
        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
            ctx.render("index.html");
        }
    }

}
