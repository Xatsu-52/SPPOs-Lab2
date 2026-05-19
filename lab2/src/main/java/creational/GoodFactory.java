package creational;

import domain.Good;

public interface GoodFactory {
    Good create(String name, double weight, double cost);
    GoodType getType();
}