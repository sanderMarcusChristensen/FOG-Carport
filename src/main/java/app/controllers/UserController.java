package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import app.services.CarportSvg;
import app.services.Svg;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Locale;

public class UserController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("homepage", ctx -> ctx.render("index.html"));

        app.get("loginPage", ctx -> ctx.render("login.html"));
        app.post("loginAccount", ctx -> login(ctx, connectionPool));    //Tager brugerens data med videre til "login"

        app.get("loginPageRequest", ctx -> ctx.render("loginRequest.html"));
        app.post("loginAccountRequest", ctx -> loginRequest(ctx, connectionPool));

        app.get("createAccountPage", ctx -> ctx.render("createAccountPage.html"));
        app.post("createAccount", ctx -> createUser(ctx, connectionPool));

        app.get("logout", ctx -> logout(ctx));

        app.get("customCarportInput", ctx -> ctx.render("customCarportInput.html"));
    }

    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

    private static void loginRequest(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");  //Bruges til at hente dataen fra brugerns input fra login-form (scanner)
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);  //Gemmer alle brugers data, så de er tilgænelige på tværs af siderne.
            ctx.sessionAttribute("currentUserId", user.getUserId());
            ctx.sessionAttribute("currentUserName", user.getUserName());
            ctx.sessionAttribute("currentUserEmail", user.getUserEmail());

            int width = ctx.sessionAttribute("width");
            int length = ctx.sessionAttribute("length");
            String type = ctx.sessionAttribute("type");

            Locale.setDefault(new Locale("US"));
            CarportSvg svg = new CarportSvg(width, length);

            Svg carportSvg = new Svg(0, 0, "0 0 855 690", "100%");  //laver SVG-tegningen.
            carportSvg.addRectangle(0, 0, width, length, "stroke:#000000; stroke-width:2px; fill: #ffffff; margin: auto;");

            ctx.attribute("svg", svg.toString());
            ctx.render("customCarportPreView.html");


        } catch (DatabaseException e) {
            //hvis login fejler, vis fejlbesked
            ctx.attribute("errormessage", e.getMessage());  //gem fejlbesked i "request scope"
            ctx.render("login.html"); //vis login-siden igen
        }
    }

    private static void login(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(email, password, connectionPool);  //bruger mapper som "checker" data op imod databsen

            //Gemmer de forskellige data i vores session scope
            ctx.sessionAttribute("currentUser", user);
            ctx.sessionAttribute("currentUserId", user.getUserId());
            ctx.sessionAttribute("currentUserName", user.getUserName());
            ctx.sessionAttribute("currentUserEmail", user.getUserEmail());

            ctx.render("index.html");

        } catch (DatabaseException e) {
            ctx.attribute("errormessage", e.getMessage());
            ctx.render("login.html");
        }

    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        String name = ctx.formParam("name");
        String password = ctx.formParam("password");
        String passwordrepeat = ctx.formParam("passwordrepeat");
        String email = ctx.formParam("email");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));
        String address = ctx.formParam("address");

        if (password.equals(passwordrepeat) && name.length() > 3 && password.length() > 3 && passwordrepeat.length() > 3) {
            try {
                User user = new User(0, name, password, email, zipcode, "user", address);
                UserMapper.insertUser(user, connectionPool);
                ctx.attribute("usermessage", "Du er hermed oprettet med e-mail: " + email + " og det angivne password. Nu skal du logge ind på din bruger");
                ctx.render("login.html");
            } catch (DatabaseException e) {
                ctx.attribute("errormessage", "Din email findes allerede. Prøv igen eller log ind");
                ctx.render("createAccountPage.html");
            }
        } else {
            ctx.attribute("errormessage", "Noget gik galt. Prøv igen. Vær sikker på følgende:\n- Password længde skal være mere end 3 tegn\n- Dine passwords skal matche i begge felter - Email skal indeholde '@' - Dit postnummer skal være gyldigt");
            ctx.render("customCarport_3.html");
        }
    }


}