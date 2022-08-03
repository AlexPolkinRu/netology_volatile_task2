import java.util.concurrent.atomic.LongAdder;

/**
 * @author Aleksandr Polochkin
 * 29.07.2022
 */

public class Main {

    static final int NUMBER_OF_MARKETS = 3;
    static final Market[] markets = new Market[NUMBER_OF_MARKETS];

    public static void main(String[] args) throws InterruptedException {

        LongAdder revenue = new LongAdder();

        for (int i = 0; i < NUMBER_OF_MARKETS; i++) {
            markets[i] = new Market("магазин №" + (i + 1));
            markets[i].start();
        }

        for (Market thread: markets) {
            thread.join();
        }

        getReports(revenue);

    }

    static void getReports(LongAdder revenue) throws InterruptedException {

        // В конце рабочего дня запускаем потоки подсчёта выручки по каждому магазину
        final MarketReport[] marketReports = new MarketReport[NUMBER_OF_MARKETS];

        for (int i = 0; i < NUMBER_OF_MARKETS; i++) {
            marketReports[i] = new MarketReport(markets[i], revenue);
            marketReports[i].start();
        }

        for (MarketReport thread: marketReports) {
            thread.join();
        }

        System.out.println("Общая выручка: " + revenue.sum());

    }

}
