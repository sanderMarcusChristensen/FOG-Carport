package app.persistence;

import app.entities.ProductVariant;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VariantMapper {

    public static List<ProductVariant> getAllVariants(ConnectionPool connectionPool) throws DatabaseException {

        List<ProductVariant> getProductVariants = new ArrayList<>();

        String sql = "select * FORM variant";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int variant_id = rs.getInt("variant_id");
                int material = rs.getInt("material ");
                int length = rs.getInt("length");
                getProductVariants.add(new ProductVariant(variant_id, material, length));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return getProductVariants;
    }


    //mere guf idk
}


