package domain;

import java.util.List;

public interface PurchaseContainer extends PurchaseComponent {

    void add(PurchaseComponent component);

    void remove(PurchaseComponent component);

    List<PurchaseComponent> getItems();
}