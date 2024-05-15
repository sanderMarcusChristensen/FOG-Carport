package app.persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Intergrationstest for OrderMapper

class OrderMapperTest {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance("","","","");

    @BeforeAll
    static void setupClass()
    {



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