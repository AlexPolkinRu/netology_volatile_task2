import java.util.concurrent.atomic.LongAdder;

/**
 * @author Aleksandr Polochkin
 * 29.07.2022
 */

public class Main {

    static final int NUMBER_OF_SHOPS = 3;
    static final Shop[] SHOPS = new Shop[NUMBER_OF_SHOPS];

    public static void main(String[] args) {

        LongAdder revenue = new LongAdder();

        generateShops();
        getReports(revenue);

    }

    static void generateShops() {
        for (int i = 0; i < NUMBER_OF_SHOPS; i++) {
            SHOPS[i] = new Shop("магазин №" + (i + 1));
            SHOPS[i].start();
        }

        // Ожидаем окончания работы запущенных потоков
        try {
            for (Shop thread : SHOPS) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void getReports(LongAdder revenue) {

        // В конце рабочего дня запускаем потоки подсчёта выручки по каждому магазину
        final ShopReport[] shopReports = new ShopReport[NUMBER_OF_SHOPS];

        for (int i = 0; i < NUMBER_OF_SHOPS; i++) {
            shopReports[i] = new ShopReport(SHOPS[i], revenue);
            shopReports[i].start();
        }

        // Ожидаем окончания работы запущенных потоков
        try {
            for (ShopReport thread : shopReports) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Общая выручка: " + revenue.sum());

    }

}
