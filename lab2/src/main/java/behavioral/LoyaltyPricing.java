package behavioral;

import domain.PurchaseComponent;

public class LoyaltyPricing implements PricingStrategy {
    private final double discountThreshold; // порог для скидки
    private final double discountRate;      // процент скидки

    public LoyaltyPricing(double threshold, double rate) {
        this.discountThreshold = threshold;
        this.discountRate = rate;
    }

    @Override
    public double calculateTotalCost(PurchaseComponent p) {
        double base = p.getTotalCost();
        return (base >= discountThreshold) ? base * (1 - discountRate) : base;
    }

    @Override public double calculateTotalWeight(PurchaseComponent p) { return p.getTotalWeight(); }
}