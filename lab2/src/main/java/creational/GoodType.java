package creational;

public enum GoodType {
    FOOD("Еда"),
    ELECTRONICS("Электроника"),
    CLOTHING("Одежда"),
    HOUSEHOLD("Товары для дома"),
    DRINK("Напиток"),
    GENERAL("Общее");

    private final String label;

    GoodType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
