package app.persistence;

import app.entities.Order;
import app.entities.OrderItem;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderItemMapper  {

    public static List<OrderItem> getAllOrder(ConnectionPool connectionPool) throws DatabaseException {

        List<OrderItem> orderItemsList = new ArrayList<>();

        String sql = "select * FORM order_item";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int order_item = rs.getInt("order_item");
                int variant_id = rs.getInt("variant_id");
                String description = rs.getString("description");
                int order_id = rs.getInt("order_id");
                orderItemsList.add(new OrderItem(order_item, variant_id, description,order_id));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return orderItemsList;
    }
}

