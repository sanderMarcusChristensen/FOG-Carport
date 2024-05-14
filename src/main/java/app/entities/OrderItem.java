package app.entities;

public class OrderItem {

    private int orderItemId;
    private Order order;
    private int orderId;
    private ProductVariant productVariant;
    private int productVariantId;
    private int quantity;
    private String description;

    public OrderItem(int orderItemId, int orderId, int productVariantId, int quantity, String description) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productVariantId = productVariantId;
        this.quantity = quantity;
        this.description = description;
    }

    public OrderItem(int orderItemId, Order order, ProductVariant productVariant, int quantity, String description) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.productVariant = productVariant;
        this.quantity = quantity;
        this.description = description;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", order=" + order +
                ", productVariant=" + productVariant +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
