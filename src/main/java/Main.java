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

        generateMarkets();
        getReports();

    }

    static void generateMarkets() {

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

    }

    static void getReports() {

        // В конце рабочего дня создаём пул потоков по количеству магазинов
        ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_MARKETS);

        List<Future<MarketReport>> futureTask = new ArrayList<>();

        // Запускаем подсчёт
        MarketReport[] marketReports = new MarketReport[NUMBER_OF_MARKETS];

        for (int i = 0; i < NUMBER_OF_MARKETS; i++) {
            String marketName = "магазин " + (i + 1);
            marketReports[i] = new MarketReport(salesMarket[i], marketName);

            futureTask.add(threadPool.submit(marketReports[i]));
        }

        // Формируем итоговый отчёт и выводим в консоль
        System.out.println("Отчёт по выручке");
        System.out.println("------------------");

        long totalAmount = 0;

        for (Future<MarketReport> marketReport: futureTask) {

            try {
                MarketReport currentReport = marketReport.get();
                totalAmount += currentReport.getRevenue();
                System.out.println(currentReport);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            System.out.println("------------------");

        }

        System.out.println("Общая выручка: " + totalAmount);

        threadPool.shutdown();

    }

}
