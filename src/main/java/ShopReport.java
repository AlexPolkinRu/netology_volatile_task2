import java.util.Arrays;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Aleksandr Polochkin
 * 30.07.2022
 */

public class ShopReport extends Thread {

    private final LongAdder REVENUE;
    private final Shop Shop;

    public ShopReport(Shop shop, LongAdder revenue) {
        REVENUE = revenue;
        Shop = shop;
    }

    @Override
    public void run() {
        System.out.println("Выручка за день " + Shop.NAME + ": "  + Shop.getDailyRevenue());
        Arrays.stream(Shop.getSales()).forEach(REVENUE::add);
    }

}
