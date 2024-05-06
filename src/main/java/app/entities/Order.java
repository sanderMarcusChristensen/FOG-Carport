package app.entities;

import java.time.LocalDate;
import java.util.Date;

public class Order {

    private int order_id;
    private int total_price;
    private boolean status;
    private double height;
    private double width;
    private double length;
    private Date date;
    private int user_id;

    public Order(int order_id, int total_price, boolean status, double height, double width, double length, Date date, int user_id) {
        this.order_id = order_id;
        this.total_price = total_price;
        this.status = status;
        this.height = height;
        this.width = width;
        this.length = length;
        this.date = date;
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", total_price=" + total_price +
                ", status=" + status +
                ", height=" + height +
                ", width=" + width +
                ", length=" + length +
                ", date=" + date +
                ", user_id=" + user_id +
                '}';
    }

}
