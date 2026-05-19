package domain;

import java.util.Objects;

public class Good {

    private final String name;
    private final double weight;
    private final double cost;

    private int ID;

    private final String type;

    public Good(String name,
                double weight,
                double cost,
                int ID,
                String type) {

        this.name = name;
        this.weight = weight;
        this.cost = cost;
        this.ID = ID;
        this.type = type;
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

    public void setID(int ID) {this.ID = ID;}

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return Double.compare(weight, good.weight) == 0 && Double.compare(cost, good.cost) == 0 && ID == good.ID && Objects.equals(name, good.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, cost, ID);
    }

    @Override
    public String toString() {
        return "Good{" +
                "name='" + name + '\'' +
                ", weight=" + weight + " грамм" +
                ", cost=" + cost + " рублей" +
                ", ID=" + ID +
                ", type='" + type + '\'' +
                '}';
    }
}

