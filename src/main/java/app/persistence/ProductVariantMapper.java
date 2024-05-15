package app.persistence;

import app.entities.Product;
import app.entities.ProductVariant;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantMapper {

    public static List<ProductVariant> getVariantsByProductIdAndMinLength(int minLength, int productId, ConnectionPool connectionPool) throws DatabaseException {

        List<ProductVariant> productVariants = new ArrayList<>();

        String sql = "SELECT * FROM product_variant " +
                "INNER JOIN product p USING(product_id) " +
                "WHERE product_id = ? AND product_variant_length >= ?";

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, minLength);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int variantId = rs.getInt("product_variant_id");
                int product_id = rs.getInt("product_id");
                int variant_length = rs.getInt("product_variant_length");
                String product_name = rs.getString("product_name");
                String unit = rs.getString("unit");
                int price = rs.getInt("price");
                Product product = new Product(product_id, product_name, unit, price);
                ProductVariant productVariant = new ProductVariant(variantId, product, variant_length);
                productVariants.add(productVariant);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get some products or productvariants", e.getMessage());
        }
        return productVariants;
    }
}



