package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.persistence.OrderMapper;
import app.services.Calculator;
import app.services.CarportSvg;
import app.services.Svg;
import io.javalin.http.Context;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.get("continueToPageThree", ctx -> ctx.render("customCarport_3.html"));      //Efter svg-tegning, bliver CustomCarport vist
        app.get("backToPageOne", ctx -> ctx.render("customCarportInput.html"));

        app.post("sendRequest", ctx -> sendRequest(ctx, connectionPool));
        app.get("sendRequestLoggedIn", ctx -> sendRequestLoggedIn(ctx, connectionPool));
        app.post("getCarportSize", ctx -> showPreView(ctx));    // th:action="@{getCarportSize} sender dataen videre ned i showPreView
        app.get("customCarportPreView", ctx -> OrderController.showPreView(ctx));

    }

    public static void showPreView(Context ctx) {
        int width = Integer.parseInt(ctx.formParam("width"));       //Gemmer brugerns data i variabler width og length
        int length = Integer.parseInt(ctx.formParam("length"));

        ctx.sessionAttribute("width", width);   //Gemmer dem på "sessionen" til opretteelsen af bruger (session scope)
        ctx.sessionAttribute("length", length);

        String type = ctx.sessionAttribute("type");     //Gemmer valget af "vag" (bruges ikke)

        //kode til svg
        Locale.setDefault(new Locale("US"));
        CarportSvg svg = new CarportSvg(width, length);

        Svg carportSvg = new Svg(0, 0, "0 0 855 690", "100%");
        carportSvg.addRectangle(0, 0, width, length, "stroke:#000000; stroke-width:2px; fill: #ffffff; margin: auto;");

        ctx.attribute("svg", svg.toString());
        ctx.render("customCarportPreView.html");

    }

    private static void sendRequest(Context ctx, ConnectionPool connectionPool) {


        int carportWidth = ctx.sessionAttribute("width");
        int carportLength = ctx.sessionAttribute("length");

        Date date = Date.valueOf(LocalDate.now());
        int status = 1;
        int totalPrice = 19999;

        String name = ctx.formParam("name");
        String password = ctx.formParam("password");
        String passwordrepeat = ctx.formParam("passwordrepeat");
        String email = ctx.formParam("email");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));
        String address = ctx.formParam("address");


        if (password.equals(passwordrepeat) && name.length() > 3 && password.length() > 3 && passwordrepeat.length() > 3) {
            try {

                User user = new User(0, name, password, email, zipcode, "user", address);
                user = UserMapper.insertUser(user, connectionPool);

                Order order = new Order(0, carportWidth, carportLength, date, status, totalPrice, user);
                order = OrderMapper.insertOrder(order, connectionPool);

                //Stopper her for en normal bruger

                //Kun admins kan se styklisten

                Calculator calculator = new Calculator(carportWidth, carportLength, connectionPool);
                calculator.calcCarport(order);


                OrderMapper.insertOrderItems(calculator.getOrderItem(), connectionPool);

                ctx.attribute("usermessage", "Du er hermed oprettet med e-mail: " + email + " - Du vil modtage en email " +
                        "med yderligere informationer vedr. din forespørgsel.");
                ctx.render("customCarport_3.html");


            } catch (DatabaseException e) {
                ctx.attribute("errormessage", "Din email findes allerede. Prøv igen eller log ind");
                ctx.render("customCarport_3.html");
            }
        } else {
            ctx.attribute("errormessage", "Noget gik galt. Prøv igen. Vær sikker på følgende:\n- Password længde skal være mere end 3 tegn\n- Dine passwords skal matche i begge felter - Email skal indeholde '@' - Dit postnummer skal være gyldigt");
            ctx.render("customCarport_3.html");
        }
    }

    private static void sendRequestLoggedIn(Context ctx, ConnectionPool connectionPool) {


        int carportWidth = ctx.sessionAttribute("width");
        int carportLength = ctx.sessionAttribute("length");

        Date date = Date.valueOf(LocalDate.now());
        int status = 1;
        int totalPrice = 19999;


        try {

            User user = ctx.sessionAttribute("currentUser");

            Order order = new Order(0, carportWidth, carportLength, date, status, totalPrice, user);
            order = OrderMapper.insertOrder(order, connectionPool);


            Calculator calculator = new Calculator(carportWidth, carportLength, connectionPool);
            calculator.calcCarport(order);


            OrderMapper.insertOrderItems(calculator.getOrderItem(), connectionPool);

            ctx.attribute("usermessage", "Du vil modtage en email på " + user.getUserEmail() +
                    " med yderligere informationer vedr. din forespørgsel.");
            ctx.render("index.html");


        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
        }


    }
}

