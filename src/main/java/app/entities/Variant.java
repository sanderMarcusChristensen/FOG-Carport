package app.entities;

public class Variant {

    private int variant_id;
    private int material_id;
    private int length;

    public Variant(int variant_id, int material_id, int length) {
        this.variant_id = variant_id;
        this.material_id = material_id;
        this.length = length;
    }

    public int getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "variant_id=" + variant_id +
                ", material_id=" + material_id +
                ", length=" + length +
                '}';
    }
}
