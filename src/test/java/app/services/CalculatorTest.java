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
     static void setop(){

        // kig på discord pin, skal være i ordercontrolelr

    }

    @Test
    void calcPostQuantity() {

        //Arrange
        Calculator calculator = new Calculator(600,780, connectionPool);

        //Expected
        int expected = 6;

        //Actual
        int actual = calculator.calcPostQuantity();

        //Assert
        assertEquals(expected, actual);


    }

    @Test
    void calcRaftersQuantity(){

        //Arrange
        Calculator calculator = new Calculator(600,750, connectionPool);

        //   50/750 = 15
        //   15 + 1 = 16

        //Expected
        int expected = 16;


        //Actual
        int actual = calculator.calcRaftersQuantity();

        //Assert
        assertEquals(expected, actual);


    }
}