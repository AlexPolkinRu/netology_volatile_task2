import java.util.Arrays;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Aleksandr Polochkin
 * 30.07.2022
 */

public class MarketReport extends Thread {

    private final LongAdder REVENUE;
    private final Market MARKET;

    public MarketReport(Market market, LongAdder revenue) {
        REVENUE = revenue;
        MARKET = market;
    }

    @Override
    public void run() {
        System.out.println("Выручка за день " + MARKET.NAME + ": "  + MARKET.getDailyRevenue());
        Arrays.stream(MARKET.getSales()).forEach(REVENUE::add);
    }

}
