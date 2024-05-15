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

import java.util.Date;
import java.util.Locale;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("continueToPageTwo", ctx -> customCarport_pagetwo(ctx));
        app.get("continueToPageThree", ctx -> ctx.render("customCarport_3.html"));
        app.get("backToPageOne", ctx -> customCarport_pageone(ctx));
        app.post("createRequest", ctx -> createRequestAndUser(ctx, connectionPool));

        app.post("getCarportSize", ctx -> showPreView(ctx));
        app.get("/customCarportPreView", ctx -> OrderController.showPreView(ctx));
    }

    public static void customCarport_pageone(Context ctx) {
        ctx.render("customCarportInput.html");
    }

    public static void customCarport_pagetwo(Context ctx) {

        int carportWidth = Integer.parseInt(ctx.formParam("width_id"));
        int carportLength = Integer.parseInt(ctx.formParam("length_id"));
        String carportRoof = ctx.formParam("roof_id");

        ctx.sessionAttribute("carportWidth", carportWidth);
        ctx.sessionAttribute("carportLength", carportLength);
        ctx.sessionAttribute("carportRoof", carportRoof);

        ctx.render("customCarport_2.html");
    }

    public static void createRequestAndUser(Context ctx, ConnectionPool connectionPool) {

        //Henter alle informationer vedrørende custom carport fra den tidligere forespørgsel
        int carportWidth = ctx.sessionAttribute("carportWidth");
        int carportLength = ctx.sessionAttribute("carportLength");
        String carportRoof = ctx.sessionAttribute("carportRoof");

        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String passwordrepeat = ctx.formParam("passwordrepeat");
        String address = ctx.formParam("address");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));
        String comment = ctx.formParam("comment");

        if (password.equals(passwordrepeat) && name.length() > 3 && password.length() > 3 && passwordrepeat.length() > 3) {
            try {
                UserMapper.createuser(name, password, email, zipcode, address, connectionPool);
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

    public static void showPreView(Context ctx){
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        String type = ctx.sessionAttribute("type");

        Locale.setDefault(new Locale("US"));
        CarportSvg svg = new CarportSvg(width, length);

        Svg carportSvg = new Svg(0, 0, "0 0 855 690", "100%" );
        carportSvg.addRectangle(0, 0, width, length, "stroke:#000000; stroke-width:2px; fill: #ffffff; margin: auto;" );

        ctx.attribute("svg", svg.toString());
        ctx.render("customCarportPreView.html");

    }

    private static void sendRequest(Context ctx, ConnectionPool connectionPool){

        //Get order details from front-end
        int width = ctx.sessionAttribute("width");
        int length = ctx.sessionAttribute("length");
        Date date = ctx.sessionAttribute("date");
        int status = 1;
        int totalPrice = 19999;
        User user = new User(1,"gud", "1234", "sanderc69@gmail.com", "2990", "user", "Nivå");   // hard-code for nu (laver en dummy user)


        Order order = new Order(0,width,length,date,status,totalPrice,user);

        try{
            order = OrderMapper.insertOrder(order, connectionPool);

            //calculate order items (stykliste)
            Calculator calculator = new Calculator(width,length,connectionPool);
            calculator.calcCarport(order);

            //save order items in database (stykeliste)
            OrderMapper.insertOrderItems(calculator.getOrderItem(), connectionPool);

            //create messge to customer and render order / request confirmation
            ctx.render("ItEllerAndetFed/dude");   // Lave om, idk

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }


    }
}
