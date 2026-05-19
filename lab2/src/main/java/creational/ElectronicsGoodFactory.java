package creational;

import org.springframework.stereotype.Component;
import domain.Good;
@Component
public class ElectronicsGoodFactory implements GoodFactory {

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
                GoodType.ELECTRONICS.getLabel()
        );
    }

    @Override
    public GoodType getType() {
        return GoodType.ELECTRONICS;
    }
}