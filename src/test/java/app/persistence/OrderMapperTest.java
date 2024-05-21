package app.persistence;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Integration test for OrderMapper

class OrderMapperTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "Teknologisk2023!";
    private static final String URL = "jdbc:postgresql://209.38.202.233:5432/%s?currentSchema=test";
    private static final String DB = "carport_jon";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);


    @BeforeAll
    static void setupClass() throws SQLException {
        {
            try (Connection connection = connectionPool.getConnection()) {
                try (Statement stmt = connection.createStatement()) {
                    // The test schema is already created, so we only need to delete/create test tables
                    stmt.execute("DROP TABLE IF EXISTS test.users");
                    stmt.execute("DROP TABLE IF EXISTS test.orders");
                    stmt.execute("DROP SEQUENCE IF EXISTS test.users_user_id_seq CASCADE;");
                    stmt.execute("DROP SEQUENCE IF EXISTS test.orders_order_id_seq CASCADE;");
                    // Create tables as copy of original public schema structure
                    stmt.execute("CREATE TABLE test.users AS (SELECT * from public.users) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.orders AS (SELECT * from public.orders) WITH NO DATA");
                    // Create sequences for auto-generating ids for users and orders
                    stmt.execute("CREATE SEQUENCE test.users_user_id_seq");
                    stmt.execute("ALTER TABLE test.users ALTER COLUMN user_id SET DEFAULT nextval('test.users_user_id_seq')");
                    stmt.execute("CREATE SEQUENCE test.orders_order_id_seq");
                    stmt.execute("ALTER TABLE test.orders ALTER COLUMN order_id SET DEFAULT nextval('test.orders_order_id_seq')");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                fail("Database connection failed");
            }
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM test.orders");
                stmt.execute("DELETE FROM test.users");

                stmt.execute("INSERT INTO test.users (user_id, user_name, user_password, user_email, user_zipcode, user_role, user_address) " +
                        "VALUES (1, 'jon', '1234', 'yapyap@gmail.com', '42000', 'user', 'helsingørvej'), " +
                        "(2, 'Sander', '1234', '123GUF@gmail.com', '42000', 'admin', 'supervej')");

                stmt.execute("INSERT INTO test.orders (order_id, carport_width, carport_length, status, total_price, user_id) " +
                        "VALUES (1, 600, 780, 1, 20000, 1), (2, 540, 700, 2, 15000, 2), (3, 480, 600, 1, 14000, 2)");

                // Set sequence to continue from the largest member_id
                stmt.execute("SELECT setval('test.orders_order_id_seq', COALESCE((SELECT MAX(order_id) + 1 FROM test.orders), 1), false)");
                stmt.execute("SELECT setval('test.users_user_id_seq', COALESCE((SELECT MAX(user_id) + 1 FROM test.users), 1), false)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database connection failed");
        }
    }

    @Test
    void getAllOrders() {

        try {
            int expected = 3;
            List<Order> orders = OrderMapper.getAllOrders(connectionPool);
            assertEquals(expected, orders.size());
        } catch (DatabaseException e) {
            fail("Database fejl: " + e.getMessage());
        }

    }

    @Test
    void getOrderById() {

        try {

            User user = new User(2, "Sander", "1234", "123GUF@gmail.com", 42000, "admin", "supervej");
            Order expected = new Order(2, 540, 700, null, 2, 15000, user);    //send dato med ned i database, idk
            Order actualOrderOrder = OrderMapper.getOrderByIdTest(2, connectionPool);
            assertEquals(expected, actualOrderOrder);
        } catch (DatabaseException e) {
            fail("Database fejl: " + e.getMessage());
        }

    }


    @Test
    void insertOrder() throws DatabaseException {

        try {

            User user = new User(2, "Sander", "1234", "123GUF@gmail.com", 42000, "admin", "supervej");
            Order expected = new Order(2, 700, 700, null, 1, 17000, user);

            expected = OrderMapper.insertOrder(expected, connectionPool);
            Order actualOrderOrder = OrderMapper.getOrderById(expected.getOrderId(), connectionPool);
            assertEquals(expected, actualOrderOrder);
        }
        catch (DatabaseException e)
        {
            fail("Database fejl: " + e.getMessage());
        }

    }
}






