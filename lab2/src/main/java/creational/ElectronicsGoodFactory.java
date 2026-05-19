package creational;

import domain.Good;

public class ElectronicsGoodFactory implements GoodFactory {

    @Override
    public Good create(String name, double weight, double cost) {
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