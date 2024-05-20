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

        app.get("continueToPageThree", ctx -> ctx.render("customCarport_3.html"));
        app.get("backToPageOne", ctx -> ctx.render("customCarportInput.html"));

        app.post("sendRequest", ctx -> sendRequest(ctx, connectionPool));
        app.get("sendRequestLoggedIn", ctx -> sendRequestLoggedIn(ctx, connectionPool));
        app.post("getCarportSize", ctx -> showPreView(ctx));
        app.get("/customCarportPreView", ctx -> OrderController.showPreView(ctx));

    }

    public static void showPreView(Context ctx) {
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));

        ctx.sessionAttribute("width", width);
        ctx.sessionAttribute("length", length);

        String type = ctx.sessionAttribute("type");

        Locale.setDefault(new Locale("US"));
        CarportSvg svg = new CarportSvg(width, length);

        Svg carportSvg = new Svg(0, 0, "0 0 855 690", "100%");
        carportSvg.addRectangle(0, 0, width, length, "stroke:#000000; stroke-width:2px; fill: #ffffff; margin: auto;");

        ctx.attribute("svg", svg.toString());
        ctx.render("customCarportPreView.html");

    }

    private static void sendRequest(Context ctx, ConnectionPool connectionPool) {

        //Get order details from front-end
        int carportWidth = ctx.sessionAttribute("width");
        int carportLength = ctx.sessionAttribute("length");
        //Set date of today
        Date date = Date.valueOf(LocalDate.now());
        int status = 1;
        int totalPrice = 19999;

        String name = ctx.formParam("name");
        String password = ctx.formParam("password");
        String passwordrepeat = ctx.formParam("passwordrepeat");
        String email = ctx.formParam("email");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));
        String address = ctx.formParam("address");

        //User user = new User();   // hard-code for nu (laver en dummy user)


        if (password.equals(passwordrepeat) && name.length() > 3 && password.length() > 3 && passwordrepeat.length() > 3) {
            try {

                User user = new User(0, name, password, email, zipcode, "user", address);
                user = UserMapper.insertUser(user, connectionPool);

                Order order = new Order(0, carportWidth, carportLength, date, status, totalPrice, user);
                order = OrderMapper.insertOrder(order, connectionPool);

                //calculate order items (stykliste)
                Calculator calculator = new Calculator(carportWidth, carportLength, connectionPool);
                calculator.calcCarport(order);

                //save order items in database (stykeliste)
                OrderMapper.insertOrderItems(calculator.getOrderItem(), connectionPool);

                ctx.attribute("usermessage", "Du er hermed oprettet med e-mail: " + email + " - Du vil modtage en email " +
                        "med yderligere informationer vedr. din forespørgsel.");
                ctx.render("customCarport_3.html");
                //create messge to customer and render order / request confirmation
                //ctx.render("ItEllerAndetFed/dude");   // Lave om, idk

            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }


        } else {
            ctx.attribute("errormessage", "Noget gik galt. Prøv igen. Vær sikker på følgende:\n- Password længde skal være mere end 3 tegn\n- Dine passwords skal matche i begge felter - Email skal indeholde '@' - Dit postnummer skal være gyldigt");
            ctx.render("customCarport_3.html");
        }
    }

    private static void sendRequestLoggedIn(Context ctx, ConnectionPool connectionPool) {

        //Get order details from front-end
        int carportWidth = ctx.sessionAttribute("width");
        int carportLength = ctx.sessionAttribute("length");
        //Set date of today
        Date date = Date.valueOf(LocalDate.now());
        int status = 1;
        int totalPrice = 19999;

        //User user = new User();   // hard-code for nu (laver en dummy user)

        try {

            User user = ctx.sessionAttribute("currentUser");

            Order order = new Order(0, carportWidth, carportLength, date, status, totalPrice, user);
            order = OrderMapper.insertOrder(order, connectionPool);

            //calculate order items (stykliste)
            Calculator calculator = new Calculator(carportWidth, carportLength, connectionPool);
            calculator.calcCarport(order);

            //save order items in database (stykeliste)
            OrderMapper.insertOrderItems(calculator.getOrderItem(), connectionPool);

            ctx.attribute("usermessage", "Du vil modtage en email på " + user.getUserEmail() +
                    "med yderligere informationer vedr. din forespørgsel.");
            ctx.render("customCarport_3.html");
            //create messge to customer and render order / request confirmation
            //ctx.render("ItEllerAndetFed/dude");   // Lave om, idk

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }


    }
}

