package app.entities;

public class Product {

    private int productId;
    private String product_name;
    private String unit;
    private double price;

    public Product(int productId, String product_name, String unit, double price) {
        this.productId = productId;
        this.product_name = product_name;
        this.unit = unit;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + product_name + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                '}';
    }
}
