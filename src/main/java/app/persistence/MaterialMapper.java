package app.persistence;

import app.entities.Product;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialMapper {

    public static List<Product> getAllMaterials(ConnectionPool connectionPool) throws DatabaseException {

        List<Product> productList = new ArrayList<>();

        String sql = "select * FORM material";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int material_id = rs.getInt("material_id");
                String type = rs.getString("type");
                double width = rs.getDouble("width");
                double height = rs.getDouble("height");
                int amount = rs.getInt("amount");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                productList.add(new Product(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return productList;
    }

    public static List<Product> getAllWoodMaterial(ConnectionPool connectionPool) throws DatabaseException {

        List<Product> woodProductList = new ArrayList<>();

        String sql = "select * FORM material WHERE type = wood";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int material_id = rs.getInt("material_id");
                String type = rs.getString("type");
                double width = rs.getDouble("width");
                double height = rs.getDouble("height");
                int amount = rs.getInt("amount");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                woodProductList.add(new Product(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return woodProductList;
    }

    public static List<Product> getAllRoofMaterial(ConnectionPool connectionPool) throws DatabaseException {

        List<Product> roofProductList = new ArrayList<>();

        String sql = "select * FORM material WHERE type = roof";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int material_id = rs.getInt("material_id");
                String type = rs.getString("type");
                double width = rs.getDouble("width");
                double height = rs.getDouble("height");
                int amount = rs.getInt("amount");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                roofProductList.add(new Product(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return roofProductList;
    }
    public static List<Product> getAllScrewsMaterial(ConnectionPool connectionPool) throws DatabaseException {

        List<Product> screwsProductList = new ArrayList<>();

        String sql = "select * FORM material WHERE type = screws";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int material_id = rs.getInt("material_id");
                String type = rs.getString("type");
                double width = rs.getDouble("width");
                double height = rs.getDouble("height");
                int amount = rs.getInt("amount");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                screwsProductList.add(new Product(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return screwsProductList;
    }
    public static List<Product> getAllFittingsMaterial(ConnectionPool connectionPool) throws DatabaseException {

        List<Product> fittingsProductList = new ArrayList<>();

        String sql = "select * FORM material WHERE type = fittings";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int material_id = rs.getInt("material_id");
                String type = rs.getString("type");
                double width = rs.getDouble("width");
                double height = rs.getDouble("height");
                int amount = rs.getInt("amount");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                fittingsProductList.add(new Product(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return fittingsProductList;
    }


}
