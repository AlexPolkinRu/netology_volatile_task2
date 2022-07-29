import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Aleksandr Polochkin
 * 29.07.2022
 */

public class Main {

    static final int NUMBER_OF_MARKETS = 3;
    static final int MIN_SALES_VALUE = 1000;
    static final int MAX_SALES_VALUE = 10000;
    static final int MIN_PRICE = 50;
    static final int MAX_PRICE = 10000;
    static int salesValue;
    static int[][] salesMarket = new int[NUMBER_OF_MARKETS][];

    public static void main(String[] args) {

        Random rnd = new Random();

        for (int i = 0; i < NUMBER_OF_MARKETS; i++) {

            // Генерируем объём продаж в i-ом магазине
            salesValue = rnd.nextInt(MAX_SALES_VALUE - MIN_SALES_VALUE) + MIN_SALES_VALUE;
            salesMarket[i] = new int[salesValue];

            // Генерируем продажи в i-ом магазине
            for (int j = 0; j < salesValue; j++) {
                salesMarket[i][j] = rnd.nextInt(MAX_PRICE - MIN_PRICE) + MIN_PRICE;
            }

        }

        // В конце рабочего дня создаём пул потоков по количеству магазинов
        ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_MARKETS);

        List<Future<String>> futureTask = new ArrayList<>();

        // Запускаем подсчёт
        for (int i = 0; i < NUMBER_OF_MARKETS; i++) {
            String marketName = "магазин " + (i + 1);
            futureTask.add(threadPool.submit(new Market(salesMarket[i], marketName)));
        }

        // Формируем итоговый отчёт и выводим в консоль
        System.out.println("Отчёт по выручке");
        System.out.println("------------------");

        for (Future<String> revenue: futureTask) {

            try {
                System.out.println(revenue.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }

        threadPool.shutdown();
    }

}
