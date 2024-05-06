package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderMapper {


    public static List<Order> getAllOrder(ConnectionPool connectionPool) throws DatabaseException {

        List<Order> orderList = new ArrayList<>();

        String sql = "select * FORM order";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int order_id = rs.getInt("order_id");
                int total_price = rs.getInt("total_price");
                boolean status = rs.getBoolean("status");
                double height = rs.getDouble("height");
                double width = rs.getDouble("width");
                double length = rs.getDouble("length");
                Date date = rs.getDate("date");
                int user_id = rs.getInt("user_id");
                orderList.add(new Order(order_id, total_price, status, height, width, length, date, user_id));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return orderList;
    }
}