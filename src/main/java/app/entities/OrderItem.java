package app.entities;

public class OrderItem {

    private int order_item_id;
    private int variant_id;
    private String request_description;
    private int order_id;

    public OrderItem(int order_item_id, int variant_id, String request_description, int order_id) {
        this.order_item_id = order_item_id;
        this.variant_id = variant_id;
        this.request_description = request_description;
        this.order_id = order_id;
    }

    public int getOrder_item() {
        return order_item_id;
    }

    public void setOrder_item(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public String getDescription() {
        return request_description;
    }

    public void setDescription(String request_description) {
        this.request_description = request_description;
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
                "order_item=" + order_item_id +
                ", variant_id=" + variant_id +
                ", description='" + request_description + '\'' +
                ", order_id=" + order_id +
                '}';
    }
}
