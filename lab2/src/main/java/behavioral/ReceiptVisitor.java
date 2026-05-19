package behavioral;

import domain.GoodItem;
import domain.Purchase;
import domain.PurchaseComponent;

public class ReceiptVisitor {

    private final StringBuilder receipt = new StringBuilder();

    public void visit(Purchase purchase) {

        receipt.append("Покупка: ")
                .append(purchase.getName())
                .append("\n");

        receipt.append("-".repeat(40))
                .append("\n");

        for (PurchaseComponent item : purchase.getItems()) {
            item.accept(this);
        }
    }

    public void visit(GoodItem item) {

        receipt.append(
                String.format(
                        "  • %-15s | %2d шт. | %6.2f руб. | %5.1f г%n",
                        item.getGood().getName(),
                        item.getCount(),
                        item.getTotalCost(),
                        item.getTotalWeight()
                )
        );
    }

    public String print() {
        return receipt.toString();
    }
}