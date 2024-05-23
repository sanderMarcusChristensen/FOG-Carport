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


        int quantity = calcPostQuantity();


        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, POSTS, connectionPool);

        ProductVariant productVariant = productVariants.get(0);

        OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, "Stolper nedgraves 90 cm i jord"); // laver et nyt OrderItem
        orderItem.add(orderItem1);

    }


    //Remme
    private void calcBeams(Order order) throws DatabaseException {

        int quantity = calcBeamsUpperAndLowerBoardsQuantity();

        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, BEAMS, connectionPool);

        String description = "Remme i sider, sadles ned i stolper";

        calcWoodLength(order, productVariants, quantity, description);

    }

    //Spær
    private void calcRafters(Order order) throws DatabaseException {

        int quantity = calcRaftersQuantity();

        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, RAFTERS, connectionPool);

        String description = "Spær monteres på rem";

        calcRafterLength(order, productVariants, quantity, description);

    }

    private void calcUpperBoardsToSides(Order order) throws DatabaseException {

        int quantity = calcBeamsUpperAndLowerBoardsQuantity();

        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, UpperANDLOWERBoards_SIDES, connectionPool);

        String description = "Over sternbrædder til siderne af carporten";

        calcWoodLength(order, productVariants, quantity, description);

    }

    private void calcLowerBoardsToSides(Order order) throws DatabaseException {

        int quantity = calcBeamsUpperAndLowerBoardsQuantity();

        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0, UpperANDLOWERBoards_SIDES, connectionPool);

        String description = "Under sternbrædder til siderne af carporten";

        calcWoodLength(order, productVariants, quantity, description);
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

    public int calcBeamsUpperAndLowerBoardsQuantity() {

        return 2;
    }

    public int calcPostQuantity() {

        return 2 * (2 + (length - 130) / 340);
    }

    private void calcWoodLength(Order order, List<ProductVariant> productVariants, int quantity, String description) throws DatabaseException {

        if (length <= 300) {
            ProductVariant productVariant = productVariants.get(0);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (length > 301 && width < 360) {
            ProductVariant productVariant = productVariants.get(1);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (length > 361 && width < 420) {
            ProductVariant productVariant = productVariants.get(2);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (length > 421 && width < 480) {
            ProductVariant productVariant = productVariants.get(3);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (length > 481 && width < 540) {
            ProductVariant productVariant = productVariants.get(4);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (length > 541) {
            ProductVariant productVariant = productVariants.get(5);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);
        }
    }

    public void calcRafterLength(Order order, List<ProductVariant> productVariants, int quantity, String description) {

        if (width <= 300) {
            ProductVariant productVariant = productVariants.get(0);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (width > 301 && width <= 360) {
            ProductVariant productVariant = productVariants.get(1);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (width > 361 && width <= 420) {
            ProductVariant productVariant = productVariants.get(2);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (width > 421 && width <= 480) {
            ProductVariant productVariant = productVariants.get(3);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (width > 481 && width <= 540) {
            ProductVariant productVariant = productVariants.get(4);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);

        } else if (width > 541) {
            ProductVariant productVariant = productVariants.get(5);
            OrderItem orderItem1 = new OrderItem(0, order, productVariant, quantity, description);
            orderItem.add(orderItem1);
        }

    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }
}