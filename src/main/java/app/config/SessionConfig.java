package app.config;

import jakarta.servlet.SessionTrackingMode;
import org.eclipse.jetty.server.session.SessionHandler;

import java.util.EnumSet;

public class SessionConfig {
    public static SessionHandler sessionConfig() {


        SessionHandler sessionHandler = new SessionHandler(); // initialisere klassen “Sessionhandler”

        sessionHandler.setUsingCookies(true);

        sessionHandler.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE)); //bruger cookies

        sessionHandler.setHttpOnly(true);
        return sessionHandler;
    }
}