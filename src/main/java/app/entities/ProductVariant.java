package app.entities;

public class ProductVariant {

    private int productVariantId;
    private Product product;
    private int product_variant_length;

    public ProductVariant(int productVariantId, Product product, int product_variant_length) {
        this.productVariantId = productVariantId;
        this.product = product;
        this.product_variant_length = product_variant_length;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public Product getProduct() {
        return product;
    }

    public int getProduct_variant_length() {
        return product_variant_length;
    }

    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setProduct_variant_length(int product_variant_length) {
        this.product_variant_length = product_variant_length;
    }

    @Override
    public String toString() {
        return "ProductVariant{" +
                "productVariantId=" + productVariantId +
                ", product=" + product +
                ", length=" + product_variant_length +
                '}';
    }
}
