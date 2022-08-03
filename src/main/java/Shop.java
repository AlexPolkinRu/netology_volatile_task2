import java.util.Random;

/**
 * @author Aleksandr Polochkin
 * 03.08.2022
 */

public class Shop extends Thread {

    static final int MIN_SALES_VALUE = 1000;
    static final int MAX_SALES_VALUE = 10000;
    static final int MIN_PRICE = 50;
    static final int MAX_PRICE = 10000;

    public final String NAME;

    private static final Random rnd = new Random();
    private int dailyRevenue = 0;

    private int[] sales;

    public Shop(String name) {
        NAME = name;
    }

    @Override
    public void run() {

        // Генерируем объём продаж в магазине
        int salesValue = rnd.nextInt(MAX_SALES_VALUE - MIN_SALES_VALUE) + MIN_SALES_VALUE;
        sales = new int[salesValue];

        // Генерируем продажи в магазине
        for (int i = 0; i < salesValue; i++) {
            sales[i] = rnd.nextInt(MAX_PRICE - MIN_PRICE) + MIN_PRICE;
            dailyRevenue += sales[i];
        }

    }

    public int[] getSales() {
        return sales;
    }

    public int getDailyRevenue() {
        return dailyRevenue;
    }

}
