package domain;

import behavioral.PricingStrategy;
import behavioral.PurchaseObserver;
import behavioral.ReceiptVisitor;
import behavioral.StandardPricing;

import java.util.ArrayList;
import java.util.List;

public class Purchase implements PurchaseContainer {

    private final String name;

    private final List<PurchaseComponent> items = new ArrayList<>();

    private final List<PurchaseObserver> observers = new ArrayList<>();

    private PricingStrategy strategy = new StandardPricing();

    public Purchase(String name) {
        this.name = name;
    }

    @Override
    public void add(PurchaseComponent component) {
        items.add(component);
        notifyObservers();
    }

    @Override
    public void remove(PurchaseComponent component) {
        items.remove(component);
        notifyObservers();
    }

    @Override
    public List<PurchaseComponent> getItems() {
        return items;
    }

    @Override
    public double getTotalCost() {
        return items.stream()
                .mapToDouble(PurchaseComponent::getTotalCost)
                .sum();
    }

    @Override
    public double getTotalWeight() {
        return items.stream()
                .mapToDouble(PurchaseComponent::getTotalWeight)
                .sum();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public void accept(ReceiptVisitor visitor) {
        visitor.visit(this);
    }

    public void addObserver(PurchaseObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        double total = getCalculatedCost();

        for (PurchaseObserver observer : observers) {
            observer.onTotalUpdated(total);
        }
    }

    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public double getCalculatedCost() {
        return strategy.calculateTotalCost(this);
    }

    public double getCalculatedWeight() {
        return strategy.calculateTotalWeight(this);
    }

    public void printStructure(String indent) {

        System.out.println(
                indent + "+ " + getName()
                        + " | Итого: "
                        + getTotalCost()
                        + " руб, "
                        + getTotalWeight()
                        + " г"
        );

        for (PurchaseComponent item : items) {

            if (item instanceof Purchase purchase) {
                purchase.printStructure(indent + "  ");
            }
            else {

                System.out.println(
                        indent + "  - "
                                + item.getName()
                                + " | "
                                + item.getTotalCost()
                                + " руб, "
                                + item.getTotalWeight()
                                + " г"
                );
            }
        }
    }
}