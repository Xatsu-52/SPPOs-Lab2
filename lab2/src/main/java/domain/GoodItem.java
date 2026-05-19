package domain;

import behavioral.ReceiptVisitor;

public class GoodItem implements PurchaseComponent {

    private final Good good;

    private int quantity;

    public GoodItem(Good good, int quantity) {
        this.good = good;
        this.quantity = quantity;
    }

    public Good getGood() {
        return good;
    }

    @Override
    public double getTotalCost() {
        return good.getCost() * quantity;
    }

    @Override
    public double getTotalWeight() {
        return good.getWeight() * quantity;
    }

    @Override
    public String getName() {
        return good.getName() + " x" + quantity;
    }

    @Override
    public int getCount() {
        return quantity;
    }

    @Override
    public void accept(ReceiptVisitor visitor) {
        visitor.visit(this);
    }
}