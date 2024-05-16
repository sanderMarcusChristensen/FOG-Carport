package app.services;

import app.entities.Order;
import app.entities.OrderItem;
import app.entities.ProductVariant;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ProductVariantMapper;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private static final int POSTS = 1;
    private static final int RAFTERS = 2;
    private static final int BEAMS = 2;
    private static final int UpperANDLOWERBoards_SIDES = 3;
    private static final int SCREWS = 4;


    private List<OrderItem> orderItem = new ArrayList<>();
    private int width;
    private int length;
    private ConnectionPool connectionPool;

    public Calculator(int width, int length, ConnectionPool connectionPool) {
        this.width = width;
        this.length = length;
        this.connectionPool = connectionPool;
    }


    public void calcCarport(Order order) throws DatabaseException {

        calcPost(order);
        calcBeams(order);
        calcRafters(order);
        calcUpperBoardsToSides(order);
        calcLowerBoardsToSides(order);
        addScrewsToOrder(order);

    }


    //Stolper
    private void calcPost(Order order) throws DatabaseException {

        //antal stolper
        int quantity = calcPostQuantity();

        //Henter alle længede på stolper - variant
        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, POSTS, connectionPool);// 0 for at hente alle stolper

        ProductVariant productVariant = productVariants.get(0); // stolpens plads i listen (kun en i listen, så skal bare på plads 1 aka 0)

        OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Stolper nedgraves 90 cm i jord"); // laver et nyt OrderItem
        orderItem.add(orderItem1); // sender det med til vores liste med all andre OrderItems

    }


    //Remme
    private void calcBeams(Order order) throws DatabaseException {

        int quantity = calcBeamsUpperAndLowerBoardsQuantity();

        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, BEAMS, connectionPool);


        if (length <= 300) {
            ProductVariant productVariant = productVariants.get(0);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Remme i sider, sadles ned i stolper");
            orderItem.add(orderItem1);

        } else if (length > 301 && width < 360) {
            ProductVariant productVariant = productVariants.get(1);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Remme i sider, sadles ned i stolper");
            orderItem.add(orderItem1);

        } else if (length > 361 && width < 420) {
            ProductVariant productVariant = productVariants.get(2);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Remme i sider, sadles ned i stolper");
            orderItem.add(orderItem1);

        } else if (length > 421 && width < 480) {
            ProductVariant productVariant = productVariants.get(3);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Remme i sider, sadles ned i stolper");
            orderItem.add(orderItem1);

        } else if (length > 481 && width < 540) {
            ProductVariant productVariant = productVariants.get(4);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Remme i sider, sadles ned i stolper");
            orderItem.add(orderItem1);

        } else if (length > 541) {
            ProductVariant productVariant = productVariants.get(5);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Remme i sider, sadles ned i stolper");
            orderItem.add(orderItem1);
        }
    }

    //Spær
    private void calcRafters(Order order) throws DatabaseException {

        int quantity = calcRaftersQuantity();

        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, RAFTERS, connectionPool);


        if (width <= 300) {
            ProductVariant productVariant = productVariants.get(0);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Spær monteres på rem");
            orderItem.add(orderItem1);

        } else if (width > 301 && width <= 360) {
            ProductVariant productVariant = productVariants.get(1);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Spær monteres på rem");
            orderItem.add(orderItem1);

        } else if (width > 361 && width <= 420) {
            ProductVariant productVariant = productVariants.get(2);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Spær monteres på rem");
            orderItem.add(orderItem1);

        } else if (width > 421 && width <= 480) {
            ProductVariant productVariant = productVariants.get(3);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Spær monteres på rem");
            orderItem.add(orderItem1);

        } else if (width > 481 && width <= 540) {
            ProductVariant productVariant = productVariants.get(4);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Spær monteres på rem");
            orderItem.add(orderItem1);

        } else if (width > 541) {
            ProductVariant productVariant = productVariants.get(5);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Spær monteres på rem");
            orderItem.add(orderItem1);
        }

    }

    private void calcUpperBoardsToSides(Order order) throws DatabaseException {

        int quantity = calcBeamsUpperAndLowerBoardsQuantity();

        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, UpperANDLOWERBoards_SIDES, connectionPool);


        if (length <= 300) {
            ProductVariant productVariant = productVariants.get(0);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Under og Oversternbrædder til siderne af carporten");
            orderItem.add(orderItem1);

        } else if (length > 301 && width < 360) {
            ProductVariant productVariant = productVariants.get(1);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Under og Oversternbrædder til siderne af carporten");
            orderItem.add(orderItem1);

        } else if (length > 361 && width < 420) {
            ProductVariant productVariant = productVariants.get(2);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Under og Oversternbrædder til siderne af carporten");
            orderItem.add(orderItem1);

        } else if (length > 421 && width < 480) {
            ProductVariant productVariant = productVariants.get(3);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Under og Oversternbrædder til siderne af carporten");
            orderItem.add(orderItem1);

        } else if (length > 481 && width < 540) {
            ProductVariant productVariant = productVariants.get(4);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Under og Oversternbrædder til siderne af carporten");
            orderItem.add(orderItem1);

        } else if (length > 541) {
            ProductVariant productVariant = productVariants.get(5);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Under og Oversternbrædder til siderne af carporten");
            orderItem.add(orderItem1);
        }
    }

    private void calcLowerBoardsToSides(Order order) throws DatabaseException {

        calcUpperBoardsToSides(order);
    }

    private void addScrewsToOrder(Order order) throws DatabaseException {

        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, SCREWS, connectionPool);

        ProductVariant productVariant = productVariants.get(0);

        OrderItem orderItem1 = new OrderItem(0, order, productVariant, 400, "Til montering og sammensættelse af træ ");
        orderItem.add(orderItem1);


    }


    public int calcRaftersQuantity() {

        return (length / 50) + 1;

    }

    //Math to Beams, Upperboards and Lowerboards
    public int calcBeamsUpperAndLowerBoardsQuantity() {

        return 2;
    }


    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public int calcPostQuantity() {  // Laver metoden sådan her for at kunne unit-test

        return 2 * (2 + (length - 130) / 340); // skal pluses med 2 på grund af 2 sider.
    }


}
