package behavioral;

import domain.PurchaseComponent;

public interface PricingStrategy {
    double calculateTotalCost(PurchaseComponent purchase);
    double calculateTotalWeight(PurchaseComponent purchase);
}