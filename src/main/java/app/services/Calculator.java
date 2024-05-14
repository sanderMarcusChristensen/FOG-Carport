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


    private List<OrderItem> orderItem = new ArrayList<>();
    private int width;
    private int length;
    private ConnectionPool connectionPool;

    public Calculator(int width, int length, ConnectionPool connectionPool) {
        this.width = width;
        this.length = length;
        this.connectionPool = connectionPool;
    }


    public void calcCarport( Order order) throws DatabaseException {

        calcPost(order);
        calcBeams(order);
        calcRafters(order);
    }



    //Stolper
    private void calcPost(Order order) throws DatabaseException{

        //antal stolper
        int quantity = calcPostQuantity();

        //Henter alle længede på stolper - variant
        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0,POSTS,connectionPool);// 0 for at hente alle stolper

        ProductVariant productVariant = productVariants.get(0); // stolpens plads i listen (kun en i listen, så skal bare på plads 1 aka 0)

        OrderItem orderItem1 = new OrderItem(0, order , productVariant, quantity, "Stolper nedgraves 90 cm i jord"); // laver et nyt OrderItem
        orderItem.add(orderItem1); // sender det med til vores liste med all andre OrderItems

    }

    public int calcPostQuantity(){  // Laver metoden sådan her for at kunne unit-test

        return 2 * (2 + (length - 130) / 340); // skal pluses med 2 på grund af 2 sider.
    }

    //Remme
    private void calcBeams(Order order){

        // hvis en carport er på 6 meter lang, kan man godt lave en rem med et stk brædt på 6 meter
        // hvis carporten er længere 6 meter, dele man den op på midden og finder det brædt som passer bedst til begge sider
        // 2 bræder til 2 sider af carporten = plus med resultatet

    }


    //Spær
    private void calcRafters(Order order) throws DatabaseException {

        int quantity = calcRaftersQuantity();

        List<ProductVariant> productVariants = ProductVariantMapper.getVariantsByProductIdAndMinLength(0,RAFTERS,connectionPool);

        if (width == 300){
            ProductVariant productVariant = productVariants.get(0);
            OrderItem orderItem1 = new OrderItem(0,order,productVariant, quantity,"Spær monteres på rem" );
            orderItem.add(orderItem1);

        } else if (width == 360 ) {
            ProductVariant productVariant = productVariants.get(1);
            OrderItem orderItem1 = new OrderItem(0,order,productVariant, quantity,"Spær monteres på rem" );
            orderItem.add(orderItem1);

        } else if (width == 420) {
            ProductVariant productVariant = productVariants.get(2);
            OrderItem orderItem1 = new OrderItem(0,order,productVariant, quantity,"Spær monteres på rem" );
            orderItem.add(orderItem1);

        } else if (width == 480) {
            ProductVariant productVariant = productVariants.get(3);
            OrderItem orderItem1 = new OrderItem(0,order,productVariant, quantity,"Spær monteres på rem" );
            orderItem.add(orderItem1);

        } else if (width == 540) {
            ProductVariant productVariant = productVariants.get(4);
            OrderItem orderItem1 = new OrderItem(0,order,productVariant, quantity,"Spær monteres på rem" );
            orderItem.add(orderItem1);

        } else if (width == 600) {
            ProductVariant productVariant = productVariants.get(5);
            OrderItem orderItem1 = new OrderItem(0,order,productVariant, quantity,"Spær monteres på rem" );
            orderItem.add(orderItem1);
        }

    }

    public int calcRaftersQuantity(){

        return (length / 50 ) + 1;
    }


    public List<OrderItem> getOrderItem() {
        return orderItem;
    }


}
