package app.controllers;

import app.entities.Order;
import app.entities.OrderItem;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


import java.util.List;


public class AdminController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("adminPage", ctx -> loadAdminPage(ctx, connectionPool));
        app.post("deleteOrder", ctx -> deleteOrder(ctx, connectionPool));
        app.post("acceptOrder", ctx -> acceptOrder(ctx, connectionPool));
        app.post("unAcceptOrder", ctx -> unAcceptOrder(ctx, connectionPool));
        app.post("orderDetails", ctx -> getOrderDetails(ctx, connectionPool));      //Tager data med videre til "getOrderDetails", 127
        app.post("updateCarportPrice", ctx -> updateOrderPrice(ctx, connectionPool));
        app.post("updateCarportSize", ctx -> updateOrderSize(ctx, connectionPool));
    }

    public static void updateOrderSize(Context ctx, ConnectionPool connectionPool) {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));

        double newWidth = Double.parseDouble(ctx.formParam("width"));
        double newLength = Double.parseDouble(ctx.formParam("length"));

        try {

            OrderMapper.updateCarportOrderWidth(orderId, newWidth, connectionPool);
            OrderMapper.updateCarportOrderLength(orderId, newLength, connectionPool);

            getOrderDetails(orderId, ctx, connectionPool);
            ctx.attribute("usermessage", "Order with OrderID: " + orderId + " changed width to: " + newWidth + " and length to: " + newLength);
            ctx.render("adminOrderPage.html");

        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
        }


    }

    public static void updateOrderPrice(Context ctx, ConnectionPool connectionPool) {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        double newPrice = Double.parseDouble(ctx.formParam("newPrice"));

        try {

            OrderMapper.updateCarportOrderTotalPrice(orderId, newPrice, connectionPool);

            getOrderDetails(orderId, ctx, connectionPool);
            ctx.attribute("usermessage", "Order with OrderID: " + orderId + " changed total price to: " + newPrice);
            ctx.render("adminOrderPage.html");

        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
        }
    }


    public static void deleteOrder(Context ctx, ConnectionPool connectionPool) {
        try {
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            OrderMapper.deleteOrderWithItems(orderId, connectionPool);

            List<Order> orderAndUserList = OrderMapper.getAllOrders(connectionPool);    //æsdmgæsdjgpsjdgpsjg
            ctx.attribute("orderAndUserList", orderAndUserList);
            ctx.attribute("usermessage", "Order #" + orderId + " has been deleted");
            ctx.render("adminPage.html");

        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
        }
    }

    public static void acceptOrder(Context ctx, ConnectionPool connectionPool) {
        try {
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            OrderMapper.acceptOrderById(orderId, connectionPool);

            List<Order> orderAndUserList = OrderMapper.getAllOrders(connectionPool);
            ctx.attribute("usermessage", "Order #" + orderId + " has been accepted");
            ctx.attribute("orderAndUserList", orderAndUserList);
            ctx.attribute("usermessage", "Order #" + orderId + " has been accepted. Automated mail has been sent to customer");
            ctx.render("adminPage.html");

        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
        }
    }

    public static void unAcceptOrder(Context ctx, ConnectionPool connectionPool) {
        try {
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            OrderMapper.unAcceptOrderById(orderId, connectionPool);

            List<Order> orderAndUserList = OrderMapper.getAllOrders(connectionPool);
            ctx.attribute("usermessage", "Order #" + orderId + " has been denied");
            ctx.attribute("orderAndUserList", orderAndUserList);
            ctx.attribute("usermessage", "Order #" + orderId + " has been denied. Automated mail has been sent to customer");
            ctx.render("adminPage.html");

        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
        }
    }

    public static void loadAdminPage(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Order> orderAndUserList = OrderMapper.getAllOrders(connectionPool);
            ctx.attribute("orderAndUserList", orderAndUserList);
            ctx.render("adminPage.html");
        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
            ctx.render("index.html");
        }
    }

    public static void getOrderDetails(Context ctx, ConnectionPool connectionPool) {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));

        try {

            List<OrderItem> orderItemList = OrderMapper.getOrderItemsByOrderId(orderId, connectionPool);
            ctx.attribute("orderItemList", orderItemList);

            Order order = OrderMapper.getOrderById(orderId, connectionPool);
            ctx.attribute("order", order);

            double normalPrice = order.getTotalPrice() + 3000;
            ctx.attribute("normalPrice", normalPrice);

            User user = UserMapper.getUserByOrderId(orderId, connectionPool);
            ctx.attribute("user", user);

            ctx.render("adminOrderPage.html");

        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
        }
    }

    public static void getOrderDetails(int orderId, Context ctx, ConnectionPool connectionPool) {

        try {

            List<OrderItem> orderItemList = OrderMapper.getOrderItemsByOrderId(orderId, connectionPool);
            ctx.attribute("orderItemList", orderItemList);

            Order order = OrderMapper.getOrderById(orderId, connectionPool);
            ctx.attribute("order", order);

            double normalPrice = order.getTotalPrice() + 3000;
            ctx.attribute("normalPrice", normalPrice);

            User user = UserMapper.getUserByOrderId(orderId, connectionPool);
            ctx.attribute("user", user);

            ctx.render("adminOrderPage.html");

        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
        }
    }

}