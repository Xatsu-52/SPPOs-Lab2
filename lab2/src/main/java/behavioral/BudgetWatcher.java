package behavioral;

public class BudgetWatcher implements PurchaseObserver {
    private final double limit;

    public BudgetWatcher(double limit) { this.limit = limit; }

    @Override public void onItemAdded(String name, double total) {
        System.out.println(" Добавлено: " + name + " | Сумма: " + total + " руб.");
    }

    @Override public void onTotalUpdated(double total) {
        if (total > limit) {
            System.out.println("Внимание! Сумма превысила лимит (" + limit + " руб.). Текущая: " + total);
            throw new RuntimeException("Превышен максимальынй допустимый чек " + limit + " рублей.");
        }
    }
}