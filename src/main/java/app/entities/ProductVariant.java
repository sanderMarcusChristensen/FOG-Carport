package app.entities;

public class ProductVariant {

    private int productVariantId;
    private Product product;
    private int length;

    public ProductVariant(int productVariantId, Product product, int length) {
        this.productVariantId = productVariantId;
        this.product = product;
        this.length = length;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public Product getProduct() {
        return product;
    }

    public int getLength() {
        return length;
    }

    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "ProductVariant{" +
                "productVariantId=" + productVariantId +
                ", product=" + product +
                ", length=" + length +
                '}';
    }
}
