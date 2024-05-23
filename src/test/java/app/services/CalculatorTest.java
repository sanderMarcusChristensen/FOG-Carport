package app.services;

import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "Teknologisk2023!";
    private static final String URL = "jdbc:postgresql://209.38.202.233:5432/%s?currentSchema=public";
    private static final String DB = "carport";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);


    @BeforeAll
    static void setup() {

    }

    @Test
    void calcPostQuantity() {

        //Arrange
        Calculator calculator = new Calculator(600, 550, connectionPool);

        //Expected
        int expected = 6;

        //Actual
        int actual = calculator.calcPostQuantity();

        //Assert
        assertEquals(expected, actual);


    }

    @Test
    void calcRaftersQuantity() {


        Calculator calculator = new Calculator(600, 600, connectionPool);

        //Expected
        int expected = 13;


        //Actual
        int actual = calculator.calcRaftersQuantity();

        //Assert
        assertEquals(expected, actual);


    }
}