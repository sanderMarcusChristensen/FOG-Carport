package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import io.javalin.http.Context;

public class UserMapper {
    private static final List<User> userList = new ArrayList<>();
    static User currentUser;

    public static User login(String user_email, String user_password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM public.\"users\" WHERE user_email=? AND user_password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, user_email);
            ps.setString(2, user_password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                String user_name = rs.getString("user_name");
                String user_zipcode = rs.getString("user_zipcode");
                String user_role = rs.getString("user_role");
                String user_address = rs.getString("user_address");
                return new User(user_id, user_name, user_password, user_email, user_zipcode, user_role, user_address);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen eller opret en bruger");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void createuser(String userName, String userPassword, String userEmail, int userZipcode, String userAddress, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO users (user_name, user_password, user_email, user_zipcode, user_role, user_address) VALUES (?,?,?,?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, userName);
            ps.setString(2, userPassword);
            ps.setString(3, userEmail);
            ps.setInt(4, userZipcode);
            ps.setString(5, "user");
            ps.setString(6, userAddress);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Email findes allerede. Vælg et andet eller log ind";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }
}