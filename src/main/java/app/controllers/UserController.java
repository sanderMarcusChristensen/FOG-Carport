package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("customCarport", ctx -> ctx.render("customCarport.html"));
        app.get("homepage", ctx -> ctx.render("index.html"));
        app.get("loginPage", ctx -> ctx.render("login.html"));
        app.get("customCarportInput", ctx -> ctx.render("customCarportInput.html"));
        app.get("createAccountPage", ctx -> ctx.render("createAccountPage.html"));
        app.post("createAccount", ctx -> createUser(ctx, connectionPool));
        app.post("loginAccount", ctx -> login(ctx, connectionPool));
    }

    private static void login(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(email, password, connectionPool);
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
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String passwordrepeat = ctx.formParam("passwordrepeat");
        String address = ctx.formParam("address");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));

        if (password.equals(passwordrepeat) && name.length() > 3 && password.length() > 3 && passwordrepeat.length() > 3) {
            try {
                UserMapper.createuser(name, password, email, zipcode, address, connectionPool);
                ctx.attribute("usermessage", "Du er hermed oprettet med e-mail: " + email + " det angivne password. Nu skal du logge ind på din bruger");
                ctx.render("login.html");
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



