package app.entities;

import java.time.LocalDate;
import java.util.Date;

public class Order {
    private int orderId;
    private double carportWidth;
    private double carportLength;
    private Date date;
    private boolean status;
    private int userId;
    private double totalPrice;
    private User user;

    public Order(int orderId, double carportWidth, double carportLength, Date date, boolean status, int userId, double totalPrice) {
        this.orderId = orderId;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.date = date;
        this.status = status;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    public Order(int orderId, double carportWidth, double carportLength, Date date, boolean status, double totalPrice, User user) {
        this.orderId = orderId;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.date = date;
        this.status = status;
        this.totalPrice = totalPrice;
        this.user = user;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getCarportWidth() {
        return carportWidth;
    }

    public double getCarportLength() {
        return carportLength;
    }

    public Date getDate() {
        return date;
    }

    public boolean isStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCarportWidth(double carportWidth) {
        this.carportWidth = carportWidth;
    }

    public void setCarportLength(double carportLength) {
        this.carportLength = carportLength;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", carportWidth=" + carportWidth +
                ", carportLength=" + carportLength +
                ", date=" + date +
                ", status=" + status +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                ", user=" + user +
                '}';
    }
}
