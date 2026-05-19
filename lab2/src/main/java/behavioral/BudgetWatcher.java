package behavioral;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BudgetWatcher implements PurchaseObserver {

    private final double limit = 100000;

    @Override
    public void onItemAdded(
            String name,
            double total
    ) {

        System.out.println(
                "Добавлено: "
                        + name
                        + " | Сумма: "
                        + total
        );
    }

    @Override
    public void onTotalUpdated(double total) {

        if (total > limit) {

            System.out.println(
                    "Превышен лимит: "
                            + total
            );

            throw new RuntimeException(
                    "Слишком большая сумма покупки"
            );
        }
    }
}