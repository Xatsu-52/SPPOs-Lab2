package structural;

import behavioral.ReceiptVisitor;
import domain.PurchaseComponent;

public abstract class PurchaseDecorator
        implements PurchaseComponent {

    protected PurchaseComponent wrapped;

    public PurchaseDecorator(
            PurchaseComponent wrapped
    ) {
        this.wrapped = wrapped;
    }

    @Override
    public double getTotalCost() {
        return wrapped.getTotalCost();
    }

    @Override
    public double getTotalWeight() {
        return wrapped.getTotalWeight();
    }

    @Override
    public String getName() {
        return wrapped.getName();
    }

    @Override
    public int getCount() {
        return wrapped.getCount();
    }

    @Override
    public void accept(ReceiptVisitor visitor) {
        wrapped.accept(visitor);
    }
}