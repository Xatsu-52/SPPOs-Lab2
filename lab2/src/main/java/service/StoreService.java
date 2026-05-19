package service;

import behavioral.*;
import creational.*;
import domain.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import structural.DiscountDecorator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class StoreService {

    private final GoodFactory foodFactory;

    private final GoodFactory drinkFactory;

    private final GoodFactory electronicsFactory;

    private final GoodFactory clothingFactory;

    private final GoodFactory householdFactory;

    private final PricingStrategy loyaltyPricing;

    private final ObjectProvider<ReceiptVisitor> visitorProvider;

    private final ObjectProvider<BudgetWatcher> watcherProvider;

    private final Scanner scanner = new Scanner(System.in);

    private final Map<Integer, StockItem> catalog = new HashMap<>();

    private final Map<Integer, GoodPrototype> prototypes = new HashMap<>();

    private int nextID = 1;

    @Autowired
    public StoreService(
            @Qualifier("foodGoodFactory")
            GoodFactory foodFactory,
            @Qualifier("drinkGoodFactory")
            GoodFactory drinkFactory,
            @Qualifier("electronicsGoodFactory")
            GoodFactory electronicsFactory,
            @Qualifier("clothingGoodFactory")
            GoodFactory clothingFactory,
            @Qualifier("houseHoldsGoodFactory")
            GoodFactory householdFactory,
            @Qualifier("loyaltyPricing")
            PricingStrategy loyaltyPricing,
            ObjectProvider<ReceiptVisitor> visitorProvider,
            ObjectProvider<BudgetWatcher> watcherProvider
    ) {

        this.foodFactory = foodFactory;
        this.drinkFactory = drinkFactory;
        this.electronicsFactory = electronicsFactory;
        this.clothingFactory = clothingFactory;
        this.householdFactory = householdFactory;
        this.loyaltyPricing = loyaltyPricing;
        this.visitorProvider = visitorProvider;
        this.watcherProvider = watcherProvider;

        seedProducts();
    }

    public void run() {

        while (true) {

            System.out.println("\n===== МАГАЗИН =====");
            System.out.println("1. Добавить товар");
            System.out.println("2. Совершить покупку");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            int choice = readInt();

            switch (choice) {

                case 1 -> addNewProduct();

                case 2 -> makePurchase();

                case 0 -> {
                    return;
                }

                default -> System.out.println("Неверный пункт меню");
            }
        }
    }

    private void seedProducts() {

        Good milk = foodFactory.create(
                "Молоко 1.5%",
                1000,
                95
        );

        milk.setID(nextID++);

        catalog.put(
                milk.getID(),
                new StockItem(milk, 30)
        );

        prototypes.put(
                milk.getID(),
                new GoodPrototype(
                        milk.getName(),
                        milk.getWeight(),
                        milk.getCost(),
                        milk.getType()
                )
        );
    }

    private void addNewProduct() {

        System.out.println("1. Создать через Factory");
        System.out.println("2. Клонировать через Prototype");
        System.out.print("Выбор: ");

        int choice = readInt();

        switch (choice) {

            case 1 -> createFromFactory();

            case 2 -> cloneProduct();
        }
    }

    private void createFromFactory() {

        System.out.print("Название: ");
        String name = scanner.nextLine();

        System.out.print("Вес: ");
        double weight = readDouble();

        System.out.print("Цена: ");
        double cost = readDouble();

        printCategories();

        int category = readInt();

        GoodFactory factory = switch (category) {

            case 1 -> foodFactory;

            case 2 -> electronicsFactory;

            case 3 -> clothingFactory;

            case 4 -> householdFactory;

            case 5 -> drinkFactory;

            default -> null;
        };

        if (factory == null) {
            return;
        }

        Good good = factory.create(name, weight, cost);

        good.setID(nextID++);

        System.out.print("Количество: ");

        int quantity = readInt();

        catalog.put(
                good.getID(),
                new StockItem(good, quantity)
        );

        prototypes.put(
                good.getID(),
                new GoodPrototype(
                        good.getName(),
                        good.getWeight(),
                        good.getCost(),
                        good.getType()
                )
        );
    }

    private void cloneProduct() {

        printCatalog();

        System.out.print("ID товара: ");

        int id = readInt();

        GoodPrototype prototype = prototypes.get(id);

        if (prototype == null) {
            return;
        }

        GoodPrototype clone = prototype.clone();

        System.out.print("Новое название: ");

        clone.setName(scanner.nextLine());

        System.out.print("Новая цена: ");

        clone.setCost(readDouble());

        clone.setID(nextID++);

        Good good = new Good(
                clone.getName(),
                clone.getWeight(),
                clone.getCost(),
                clone.getID(),
                clone.getType()
        );

        System.out.print("Количество: ");

        int quantity = readInt();

        catalog.put(
                good.getID(),
                new StockItem(good, quantity)
        );
    }

    private void makePurchase() {

        Purchase cart = new Purchase("Корзина");

        BudgetWatcher watcher =
                watcherProvider.getObject();

        cart.addObserver(watcher);

        while (true) {

            printCatalog();

            System.out.println("0. Оплатить");

            System.out.print("ID товара: ");

            int id = readInt();

            if (id == 0) {
                break;
            }

            StockItem stockItem = catalog.get(id);

            if (stockItem == null) {
                continue;
            }

            System.out.print("Количество: ");

            int quantity = readInt();

            if (quantity > stockItem.getQuantity()) {
                System.out.println("Недостаточно товара");
                continue;
            }

            stockItem.removeQuantity(quantity);

            cart.add(
                    new GoodItem(
                            stockItem.getGood(),
                            quantity
                    )
            );
        }

        checkout(cart);
    }

    private void checkout(Purchase cart) {

        cart.setStrategy(loyaltyPricing);

        double loyaltyPrice =
                cart.getCalculatedCost();

        DiscountDecorator discount =
                new DiscountDecorator(
                        cart,
                        0.10
                );

        double finalPrice =
                discount.getTotalCost();

        System.out.println(
                "\nСтоимость: "
                        + loyaltyPrice
        );

        System.out.println(
                "Со скидкой: "
                        + finalPrice
        );

        System.out.println(
                "Вес: "
                        + cart.getTotalWeight()
        );

        ReceiptVisitor visitor =
                visitorProvider.getObject();

        cart.accept(visitor);

        System.out.println(visitor.print());
    }

    private void printCatalog() {

        System.out.println("\n===== КАТАЛОГ =====");

        for (StockItem item : catalog.values()) {

            Good good = item.getGood();

            System.out.println(
                    "ID=" + good.getID()
                            + " | "
                            + good.getName()
                            + " | "
                            + good.getType()
                            + " | "
                            + good.getCost()
                            + " руб | Остаток="
                            + item.getQuantity()
            );
        }
    }

    private void printCategories() {

        System.out.println("1. Еда");
        System.out.println("2. Электроника");
        System.out.println("3. Одежда");
        System.out.println("4. Товары для дома");
        System.out.println("5. Напитки");
        System.out.print("Выбор: ");
    }

    private int readInt() {

        while (true) {

            try {
                return Integer.parseInt(scanner.nextLine());
            }
            catch (Exception e) {
                System.out.print("Введите число: ");
            }
        }
    }

    private double readDouble() {

        while (true) {

            try {
                return Double.parseDouble(scanner.nextLine());
            }
            catch (Exception e) {
                System.out.print("Введите число: ");
            }
        }
    }
}