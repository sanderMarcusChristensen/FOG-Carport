package app.persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

//Intergrationstest for OrderMapper

class OrderMapperTest {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance("","","","");

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
                    // Create sequences for auto generating id's for users and orders
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
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void getOrderItemsByOrderId() {
    }

    @Test
    void insertOrder() {
    }
}