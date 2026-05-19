package behavioral;

import domain.PurchaseComponent;
import org.springframework.stereotype.Component;

@Component
public class LoyaltyPricing implements PricingStrategy {

    private final double discountThreshold = 10000;

    private final double discountRate = 0.15;

    @Override
    public double calculateTotalCost(PurchaseComponent p) {

        double base = p.getTotalCost();

        return (base >= discountThreshold)
                ? base * (1 - discountRate)
                : base;
    }

    @Override
    public double calculateTotalWeight(PurchaseComponent p) {

        return p.getTotalWeight();
    }
}