package app.persistence;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    private static final List<User> userList = new ArrayList<>();
    static User currentUser;

    public static User login(String user_email, String user_password, ConnectionPool connectionPool) throws DatabaseException {
        String emailCheckSql = "SELECT * FROM public.\"users\" WHERE user_email=?";
        String loginSql = "SELECT * FROM public.\"users\" WHERE user_email=? AND user_password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement emailCheckPs = connection.prepareStatement(emailCheckSql);
                PreparedStatement loginPs = connection.prepareStatement(loginSql)
        ) {
            emailCheckPs.setString(1, user_email);
            ResultSet emailCheckRs = emailCheckPs.executeQuery();
            if (emailCheckRs.next()) {
                // Email exists, check password
                loginPs.setString(1, user_email);
                loginPs.setString(2, user_password);

                ResultSet loginRs = loginPs.executeQuery();
                if (loginRs.next()) {
                    int user_id = loginRs.getInt("user_id");
                    String user_name = loginRs.getString("user_name");
                    int user_zipcode = loginRs.getInt("user_zipcode");
                    String user_role = loginRs.getString("user_role");
                    String user_address = loginRs.getString("user_address");
                    return new User(user_id, user_name, user_password, user_email, user_zipcode, user_role, user_address);
                } else {
                    throw new DatabaseException("Wrong password");
                }
            } else {
                throw new DatabaseException("Email doesn't exist");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }


    public static User insertUser(User user, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO users (user_name, user_password, user_email, user_zipcode, user_role, user_address)" +
                "VALUES (?,?,?,?,?,?)";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, user.getUserName());
                ps.setString(2, user.getUserPassword());
                ps.setString(3, user.getUserEmail());
                ps.setInt(4, user.getZipcode());
                ps.setString(5, user.getUserRole());
                ps.setString(6, user.getUserAddress());
                ps.executeUpdate();
                ResultSet keySet = ps.getGeneratedKeys();
                if (keySet.next()) {
                    User newUser = new User(keySet.getInt(1), user.getUserName(), user.getUserPassword(), user.getUserEmail(), user.getZipcode(),
                            user.getUserRole(), user.getUserAddress());
                    return newUser;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not create user", e.getMessage());
        }
    }
}