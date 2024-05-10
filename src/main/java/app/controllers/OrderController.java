package app.controllers;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("continueToPageTwo", ctx -> customCarport_pagetwo(ctx));
        app.get("continueToPageThree", ctx -> ctx.render("customCarport_3.html"));
        app.get("backToPageOne", ctx -> customCarport_pageone(ctx));
        app.post("createRequest", ctx -> createRequestAndUser(ctx, connectionPool));
    }

    public static void customCarport_pageone(Context ctx) {
        ctx.render("customCarport.html");
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

}





