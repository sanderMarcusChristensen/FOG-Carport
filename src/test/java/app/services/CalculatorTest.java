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

    }

    @Test
    void calcPostQuantity() {

        Calculator calculator = new Calculator(600,780, connectionPool);

        assertEquals(6, calculator.calcPostQuantity()); // kig på discord pin, skal være i ordercontrolelr


    }
}