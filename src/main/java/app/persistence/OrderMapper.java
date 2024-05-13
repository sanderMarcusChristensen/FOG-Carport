package app.persistence;

import app.entities.*;
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


    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {

        List<Order> orderList = new ArrayList<>();

        String sql = "SELECT * FROM order INNER JOIN users using (user_id)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String userPassword = rs.getString("user_password");
                String userEmail = rs.getString("user_email");
                String userZipCode = rs.getString("user_zipcode");
                String userRole = rs.getString("user_role");
                String userAddress = rs.getString("user_address");
                int orderId = rs.getInt("order_id");
                double carportWidth = rs.getDouble("carport_width");
                double carportLength = rs.getDouble("carport_length");
                Date date = rs.getDate("date");
                boolean status = rs.getBoolean("status");
                int totalPrice = rs.getInt("total_price");
                User user = new User(userId, userName, userPassword, userEmail, userZipCode, userRole, userAddress);
                Order order = new Order(orderId, carportWidth, carportLength, date, status, totalPrice, user);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return orderList;
    }

    public static List<OrderItem> getOrderItemsByOrderId(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        List<OrderItem> orderItemList = new ArrayList<>();

        String sql = "SELECT * FROM bill_of_materials_view WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                //Order
                double carportWidth = rs.getDouble("carport_width");
                double carportLength = rs.getDouble("carport_length");
                Date date = rs.getDate("date");
                boolean status = rs.getBoolean("status");
                int userId = rs.getInt("user_id");
                double totalPrice = rs.getDouble("total_price");
                Order order = new Order(orderId, carportWidth, carportLength, date, status, totalPrice, null);

                //Product
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                double price = rs.getDouble("price");
                Product product = new Product(productId, name, unit, price);

                //Product variant
                int productVariantId = rs.getInt("product_variant_id");
                String description = rs.getString("description");
                int length = rs.getInt("length");
                ProductVariant productVariant = new ProductVariant(productVariantId, product, length);

                //OrderItem
                int orderItemId = rs.getInt("order_item_id");
                int quantity = rs.getInt("quantity");
                OrderItem orderItem = new OrderItem(orderItemId, order, productVariant, quantity, description);

                orderItemList.add(orderItem);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get data from the database", e.getMessage());
        }

        return orderItemList;
    }
}
