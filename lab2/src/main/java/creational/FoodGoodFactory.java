package creational;

import org.springframework.stereotype.Component;
import domain.Good;

@Component
public class FoodGoodFactory implements GoodFactory {

    @Override
    public Good create(
            String name,
            double weight,
            double cost
    ) {

        return new Good(
                name,
                weight,
                cost,
                -1,
                GoodType.FOOD.getLabel()
        );
    }

    @Override
    public GoodType getType() {
        return GoodType.FOOD;
    }
}