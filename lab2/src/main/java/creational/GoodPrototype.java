package creational;

public class GoodPrototype implements Cloneable {

    private String name;

    private double weight;

    private double cost;

    private String type;

    private int ID;

    public GoodPrototype(
            String name,
            double weight,
            double cost,
            String type
    ) {

        this.name = name;
        this.weight = weight;
        this.cost = cost;
        this.type = type;
        this.ID = -1;
    }

    @Override
    public GoodPrototype clone() {

        try {
            return (GoodPrototype) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public GoodPrototype setName(String name) {
        this.name = name;
        return this;
    }

    public GoodPrototype setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public GoodPrototype setID(int ID) {
        this.ID = ID;
        return this;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getCost() {
        return cost;
    }

    public String getType() {
        return type;
    }

    public int getID() {
        return ID;
    }
}