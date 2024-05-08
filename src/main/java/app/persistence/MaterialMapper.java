package app.persistence;

import app.entities.Material;
import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaterialMapper {

    public static List<Material> getAllMaterials(ConnectionPool connectionPool) throws DatabaseException {

        List<Material> materialList = new ArrayList<>();

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
                int unit = rs.getInt("unit");
                materialList.add(new Material(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return materialList;
    }

    public static List<Material> getAllWoodMaterial(ConnectionPool connectionPool) throws DatabaseException {

        List<Material> woodMaterialList = new ArrayList<>();

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
                int unit = rs.getInt("unit");
                woodMaterialList.add(new Material(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return woodMaterialList;
    }

    public static List<Material> getAllRoofMaterial(ConnectionPool connectionPool) throws DatabaseException {

        List<Material> roofMaterialList = new ArrayList<>();

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
                int unit = rs.getInt("unit");
                roofMaterialList.add(new Material(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return roofMaterialList;
    }
    public static List<Material> getAllScrewsMaterial(ConnectionPool connectionPool) throws DatabaseException {

        List<Material> screwsMaterialList = new ArrayList<>();

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
                int unit = rs.getInt("unit");
                screwsMaterialList.add(new Material(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return screwsMaterialList;
    }
    public static List<Material> getAllFittingsMaterial(ConnectionPool connectionPool) throws DatabaseException {

        List<Material> fittingsMaterialList = new ArrayList<>();

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
                int unit = rs.getInt("unit");
                fittingsMaterialList.add(new Material(material_id, type, width, height, amount, price, description, unit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while retrieving orders: " + e.getMessage());
        }

        return fittingsMaterialList;
    }


}
