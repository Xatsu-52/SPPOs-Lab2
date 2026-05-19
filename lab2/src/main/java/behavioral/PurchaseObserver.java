package behavioral;

public interface PurchaseObserver {
    void onItemAdded(String itemName, double currentTotal);
    void onTotalUpdated(double newTotal);
}