package app.entities;

public class Material {

    private int material_id;
    private String type;
    private double width;
    private double height;
    private int amount;
    private double price;
    private String description;
    private String unit;

    public Material(int material_id, String type, double width, double height, int amount, double price, String description, String unit) {
        this.material_id = material_id;
        this.type = type;
        this.width = width;
        this.height = height;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.unit = unit;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Material{" +
                "material_id=" + material_id +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", amount=" + amount +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", unit=" + unit +
                '}';
    }
}
