package behavioral;

import domain.PurchaseComponent;
import org.springframework.stereotype.Component;

@Component
public class StandardPricing implements PricingStrategy {

    @Override
    public double calculateTotalCost(PurchaseComponent p) {

        return p.getTotalCost();
    }

    @Override
    public double calculateTotalWeight(PurchaseComponent p) {

        return p.getTotalWeight();
    }
}