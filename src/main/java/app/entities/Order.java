package app.entities;

import java.util.Date;
import java.util.Objects;

public class Order {
    private int orderId;
    private double carportWidth;
    private double carportLength;
    private Date date;
    private int status;
    private int userId;
    private double totalPrice;
    private User user;

    public Order(int orderId, double carportWidth, double carportLength, Date date, int status, int userId, double totalPrice) {
        this.orderId = orderId;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.date = date;
        this.status = status;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    public Order(int orderId, double carportWidth, double carportLength, Date date, int status, double totalPrice, User user) {
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

    public int getStatus() {
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

    public void setStatus(int status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return orderId == order.orderId && Double.compare(carportWidth, order.carportWidth) == 0 && Double.compare(carportLength, order.carportLength) == 0 && status == order.status && userId == order.userId && Double.compare(totalPrice, order.totalPrice) == 0 && Objects.equals(date, order.date) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, carportWidth, carportLength, date, status, userId, totalPrice, user);
    }
}