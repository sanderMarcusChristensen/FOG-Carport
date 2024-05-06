package app.entities;

public class OrderItem {

    private int order_item;
    private int variant_id;
    private String description;
    private int order_id;

    public OrderItem(int order_item, int variant_id, String description, int order_id) {
        this.order_item = order_item;
        this.variant_id = variant_id;
        this.description = description;
        this.order_id = order_id;
    }

    public int getOrder_item() {
        return order_item;
    }

    public void setOrder_item(int order_item) {
        this.order_item = order_item;
    }

    public int getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "order_item=" + order_item +
                ", variant_id=" + variant_id +
                ", description='" + description + '\'' +
                ", order_id=" + order_id +
                '}';
    }
}
