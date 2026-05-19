package domain;

public class StockItem {

    private final Good good;

    private int quantity;

    public StockItem(Good good, int quantity) {
        this.good = good;
        this.quantity = quantity;
    }

    public Good getGood() {
        return good;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        quantity += amount;
    }

    public void removeQuantity(int amount) {
        quantity -= amount;
    }

    @Override
    public String toString() {
        return good + ", остаток=" + quantity;
    }
}