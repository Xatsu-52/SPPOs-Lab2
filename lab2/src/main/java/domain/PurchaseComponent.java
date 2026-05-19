package domain;

import behavioral.ReceiptVisitor;

public interface PurchaseComponent {

    double getTotalCost();

    double getTotalWeight();

    String getName();

    int getCount();

    void accept(ReceiptVisitor visitor);
}