package structural;
import domain.PurchaseComponent;

public class DiscountDecorator extends PurchaseDecorator {
    private final double percent;
    public DiscountDecorator(PurchaseComponent wrapped, double percent) {
        super(wrapped);
        this.percent = percent;
    }
    @Override public double getTotalCost() { return wrapped.getTotalCost() * (1 - percent); }
    @Override public String getName() { return wrapped.getName() + " (-" + (int)(percent*100) + "%)"; }

}