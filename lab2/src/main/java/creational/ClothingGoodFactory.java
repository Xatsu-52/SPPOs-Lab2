package creational;

import domain.Good;

public class ClothingGoodFactory implements GoodFactory {

    @Override
    public Good create(String name, double weight, double cost) {
        return new Good(
                name,
                weight,
                cost,
                -1,
                GoodType.CLOTHING.getLabel()
        );
    }

    @Override
    public GoodType getType() {
        return GoodType.CLOTHING;
    }
}