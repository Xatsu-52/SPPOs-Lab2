import behavioral.BudgetWatcher;
import behavioral.LoyaltyPricing;
import behavioral.ReceiptVisitor;
import creational.*;
import domain.*;
import structural.DiscountDecorator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Map<Integer, StockItem> catalog = new HashMap<>();

    private static final Map<Integer, GoodPrototype> prototypes = new HashMap<>();

    private static int nextID = 1;

    public static void main(String[] args) {

        seedProducts();

        while (true) {

            printMainMenu();

            int choice = readInt();

            switch (choice) {

                case 1 -> addNewProduct();

                case 2 -> makePurchase();

                case 0 -> {
                    System.out.println("Выход...");
                    return;
                }

                default -> System.out.println("Неверный пункт меню");
            }
        }
    }

    private static void printMainMenu() {

        System.out.println("\nДобро пожаловать в МАГАЗИН");

        System.out.println("1. Добавить товар");

        System.out.println("2. Совершить покупку");

        System.out.println("0. Выход");

        System.out.print("Выбор: ");
    }

    private static void seedProducts() {

        Good milk_Nina = new GoodBuilder("Молоко от Нины", 1000.0f, 100.0f).ID(1).type("Напиток").build();


        catalog.put(
                milk_Nina.getID(),
                new StockItem(milk_Nina, 30)
        );

        prototypes.put(
                milk_Nina.getID(),
                new GoodPrototype(
                        milk_Nina.getName(),
                        milk_Nina.getWeight(),
                        milk_Nina.getCost(),
                        milk_Nina.getType()
                )
        );
        nextID++;
    }

    private static void addNewProduct() {

        System.out.println("\n1. Создать через Factory");

        System.out.println("2. Клонировать через Prototype");

        System.out.print("Выбор: ");

        int choice = readInt();

        switch (choice) {

            case 1 -> createFromFactory();

            case 2 -> cloneProduct();

            default -> System.out.println("Неверный выбор");
        }
    }

    private static void createFromFactory() {

        System.out.print("Название: ");

        String name = scanner.nextLine();

        System.out.print("Вес: ");

        double weight = readDouble();

        System.out.print("Цена: ");

        double cost = readDouble();

        printCategories();

        int typeChoice = readInt();

        GoodFactory factory = switch (typeChoice) {

            case 1 -> new FoodGoodFactory();

            case 2 -> new ElectronicsGoodFactory();

            case 3 -> new ClothingGoodFactory();

            case 4 -> new HouseHoldsGoodFactory();

            case 5 -> new DrinkGoodFactory();

            default -> null;
        };

        if (factory == null) {

            System.out.println("Неверная категория");

            return;
        }

        Good good = factory.create(name, weight, cost);

        good.setID(nextID++);

        System.out.print("Количество на складе: ");

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

        System.out.println("Товар создан");

        System.out.println(good);
    }

    private static void cloneProduct() {

        printCatalog();

        System.out.print("ID товара для клонирования: ");

        int id = readInt();

        GoodPrototype prototype = prototypes.get(id);

        if (prototype == null) {

            System.out.println("Прототип не найден");

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

        System.out.print("Количество на складе: ");

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

        System.out.println("Товар клонирован");

        System.out.println(good);
    }

    private static void makePurchase() {

        Purchase cart = new Purchase("Корзина");

        cart.addObserver(new BudgetWatcher(100000));

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

                System.out.println("Товар не найден");

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

            System.out.println("Товар добавлен в корзину");
        }

        checkout(cart);
    }

    private static void checkout(Purchase cart) {

        System.out.println("\nРасчет оплаты ");

        System.out.println("Базовая стоимость: "
                + cart.getTotalCost());

        cart.setStrategy(
                new LoyaltyPricing(10000, 0.15)
        );

        double loyaltyPrice =
                cart.getCalculatedCost();

        PurchaseComponent discounted =
                new DiscountDecorator(cart, 0.10);

        double finalPrice =
                discounted.getTotalCost();

        System.out.println(
                "Стоимость по лояльности: "
                        + loyaltyPrice
        );

        System.out.println(
                "Стоимость со скидкой: "
                        + finalPrice
        );

        System.out.println(
                "Общий вес: "
                        + cart.getTotalWeight()
                        + " г"
        );

        System.out.println("\nЧЕК ");

        ReceiptVisitor visitor =
                new ReceiptVisitor();

        cart.accept(visitor);

        System.out.println(visitor.print());

        System.out.println(
                "ИТОГО: "
                        + finalPrice
                        + " руб."
        );
    }

    private static void printCatalog() {

        System.out.println("\nКАТАЛОГ \nВ связи с ограничениями транзакций со сторны банка\nМаксимальная сумма 1 чека - 100000 руб.\nПрограмма лояльности применяется при покупке от 50000 руб");

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
                            + " руб | "
                            + good.getWeight()
                            + " г | Остаток="
                            + item.getQuantity()
            );
        }
    }

    private static void printCategories() {

        System.out.println("\nКатегории:");

        System.out.println("1. Еда");

        System.out.println("2. Электроника");

        System.out.println("3. Одежда");

        System.out.println("4. Товары для дома");

        System.out.println("5. Напитки");

        System.out.print("Выбор: ");
    }

    private static int readInt() {

        while (true) {

            try {

                return Integer.parseInt(
                        scanner.nextLine()
                );
            }
            catch (Exception e) {

                System.out.print("Введите число: ");
            }
        }
    }

    private static double readDouble() {

        while (true) {

            try {

                return Double.parseDouble(
                        scanner.nextLine()
                );
            }
            catch (Exception e) {

                System.out.print("Введите число: ");
            }
        }
    }
}