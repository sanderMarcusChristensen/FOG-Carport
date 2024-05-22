package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import static app.persistence.UserMapper.getUserById;

public class OrderMapper {


    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {

        List<Order> orderList = new ArrayList<>();

        String sql = "SELECT * FROM orders INNER JOIN users using (user_id)";

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
                int userZipCode = rs.getInt("user_zipcode");
                String userRole = rs.getString("user_role");
                String userAddress = rs.getString("user_address");
                int orderId = rs.getInt("order_id");
                double carportWidth = rs.getDouble("carport_width");
                double carportLength = rs.getDouble("carport_length");
                Date date = rs.getDate("date");
                int status = rs.getInt("status");
                int totalPrice = rs.getInt("total_price");
                User user = new User(userId, userName, userPassword, userEmail, userZipCode, userRole, userAddress);
                Order order = new Order(orderId, carportWidth, carportLength, date, status, totalPrice, user);

                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return orderList;
    }

    public static Order getOrderById(int orderId, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "SELECT * FROM orders INNER JOIN users using (user_id) WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String userPassword = rs.getString("user_password");
                String userEmail = rs.getString("user_email");
                int userZipCode = rs.getInt("user_zipcode");
                String userRole = rs.getString("user_role");
                String userAddress = rs.getString("user_address");

                double carportWidth = rs.getDouble("carport_width");
                double carportLength = rs.getDouble("carport_length");
                Date date = rs.getDate("date");
                int status = rs.getInt("status");
                int totalPrice = rs.getInt("total_price");

                User user = new User(userId, userName, userPassword, userEmail, userZipCode, userRole, userAddress);
                return new Order(orderId, carportWidth, carportLength, date, status, totalPrice, user);
            } else {
                throw new DatabaseException("Order with ID " + orderId + " not found.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving order by ID: " + e.getMessage());
        }
    }

    public static List<OrderItem> getOrderItemsByOrderId(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        List<OrderItem> orderItemList = new ArrayList<>();

        String sql = "SELECT product_variant.product_id," +
                "io.product_variant_id," +
                "orders.order_id," +
                "orders.carport_width," +
                "orders.carport_length," +
                "orders.status," +
                "orders.date," +
                "orders.user_id," +
                "orders.total_price," +
                "io.order_item_id," +
                "io.quantity," +
                "io.description," +
                "product_variant.product_variant_length," +
                "product.product_name," +
                "product.unit," +
                "product.price " +
                "FROM orders " +
                "JOIN order_item io USING (order_id) " +
                "JOIN product_variant USING (product_variant_id) " +
                "JOIN product USING (product_id) " +
                "WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                //Order
                double carportWidth = rs.getDouble("carport_width");
                double carportLength = rs.getDouble("carport_length");
                Date date = rs.getDate("date");
                int status = rs.getInt("status");
                int userId = rs.getInt("user_id");
                double totalPrice = rs.getDouble("total_price");
                Order order = new Order(orderId, carportWidth, carportLength, date, status, userId, totalPrice);

                //Product
                int productId = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String unit = rs.getString("unit");
                double price = rs.getDouble("price");
                Product product = new Product(productId, name, unit, price);

                //Product variant
                int productVariantId = rs.getInt("product_variant_id");
                int length = rs.getInt("product_variant_length");
                ProductVariant productVariant = new ProductVariant(productVariantId, product, length);

                //OrderItem
                int orderItemId = rs.getInt("order_item_id");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                OrderItem orderItem = new OrderItem(orderItemId, order, productVariant, quantity, description);

                orderItemList.add(orderItem);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get data from the database", e.getMessage());
        }

        return orderItemList;
    }

    public static Order insertOrder(Order order, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "INSERT INTO orders (carport_width, carport_length, date, status, user_id, total_price) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setDouble(1, order.getCarportWidth());
                ps.setDouble(2, order.getCarportLength());
                ps.setDate(3, (Date) order.getDate());
                ps.setInt(4, 1);
                ps.setInt(5, order.getUser().getUserId());
                ps.setDouble(6, order.getTotalPrice());
                ps.executeUpdate();
                ResultSet keySet = ps.getGeneratedKeys();
                if (keySet.next()) {
                    Order newOrder = new Order(keySet.getInt(1), order.getCarportWidth(), order.getCarportLength(), order.getDate(), order.getStatus(),
                            order.getTotalPrice(), order.getUser());
                    return newOrder;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not create order", e.getMessage());
        }
    }

    public static void insertOrderItems(List<OrderItem> orderItems, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO order_item (order_id, product_variant_id, quantity, description) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection()) {
            for (OrderItem orderItem : orderItems) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, orderItem.getOrder().getOrderId());
                    ps.setInt(2, orderItem.getProductVariant().getProductVariantId());
                    ps.setInt(3, orderItem.getQuantity());
                    ps.setString(4, orderItem.getDescription());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not create orderitem in the database", e.getMessage());
        }
    }

    public static void deleteOrderItemsByOrderId(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM order_item WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Could not delete order items from the database", e.getMessage());
        }
    }

    public static void deleteOrderById(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Could not delete order from the database", e.getMessage());
        }
    }

    public static void deleteOrderWithItems(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        deleteOrderItemsByOrderId(orderId, connectionPool);
        deleteOrderById(orderId, connectionPool);
    }

    public static void acceptOrderById(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET status = 2 WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("No order found with the provided order ID.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not update the status of the order", e.getMessage());
        }
    }

    public static void unAcceptOrderById(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET status = 1 WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("No order found with the provided order ID.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not update the status of the order", e.getMessage());
        }
    }


    public static void updateCarportOrderTotalPrice(int orderId, double newTotalPrice, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET total_price = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, newTotalPrice);
            ps.setInt(2, orderId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("No order found with the provided order ID.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not update the total price of the order", e.getMessage());
        }
    }

    public static void updateCarportOrderWidth(int orderId, double newWidth, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET carport_width = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, newWidth);
            ps.setInt(2, orderId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("No order found with the provided order ID.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not update the width of the order", e.getMessage());
        }
    }

    public static void updateCarportOrderLength(int orderId, double newLength, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET carport_length = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, newLength);
            ps.setInt(2, orderId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("No order found with the provided order ID.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not update the length of the order", e.getMessage());
        }
    }

    public static void updateOrderStatus(int orderId, int newStatus, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, newStatus);
            ps.setInt(2, orderId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("No order found with the provided order ID.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not update the status of the order", e.getMessage());
        }
    }


    public static Order getOrderByIdTest(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        Order order = null;

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);  // Set the value for the first parameter
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Retrieve order details
                double carportWidth = rs.getDouble("carport_width");
                double carportLength = rs.getDouble("carport_length");
                Date date = rs.getDate("date");
                int status = rs.getInt("status");
                int userId = rs.getInt("user_id");
                double totalPrice = rs.getDouble("total_price");

                // Assuming a method getUserById exists to retrieve a User object by ID
                User user = UserMapper.getUserById(userId, connectionPool);

                order = new Order(orderId, carportWidth, carportLength, date, status, totalPrice, user);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get data from the database", e.getMessage());
        }

        return order;
    }
}

