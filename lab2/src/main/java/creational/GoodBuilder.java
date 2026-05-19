package creational;

import domain.Good;

public class GoodBuilder {
    //обязательный парпметры
    private final String name;
    private final double weight;
    private final double cost;
    //опциональне параметры
    private int ID = -1;
    private String type = "Общее";

    public GoodBuilder(String name, double weight, double cost) {
        this.name = name;
        this.weight = weight;
        this.cost = cost;
    }

    public GoodBuilder ID(int ID) {
        this.ID = ID;
        return this;
    }

    public GoodBuilder type(String type) {
        this.type = type;
        return this;
    }

    public Good build() {
        return new Good(name, weight, cost, ID, type);
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

    public int getID() {
        return ID;
    }
    public String getType() {
        return type;
    }
}
