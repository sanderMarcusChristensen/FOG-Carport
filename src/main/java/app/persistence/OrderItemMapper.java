package app.persistence;


import app.entities.OrderItem;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class OrderItemMapper {

    public static List<OrderItem> getAllOrderItems(ConnectionPool connectionPool) throws DatabaseException {

        List<OrderItem> orderItemsList = new ArrayList<>();

        String sql = "SELECT * FROM order_item";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int orderItemId = rs.getInt("order_item_id");
                int orderId = rs.getInt("order_id");
                int productVariantId = rs.getInt("product_variant_id");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                OrderItem orderItem = new OrderItem(orderItemId, orderId, productVariantId, quantity, description);
                orderItemsList.add(orderItem);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return orderItemsList;
    }
}