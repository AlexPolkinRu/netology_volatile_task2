import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Aleksandr Polochkin
 * 29.07.2022
 */

public class Market implements Callable<String> {

    private final String MARKET_NAME;
    private final int[] SALES_MARKET;

    LongAdder revenue = new LongAdder();

    public Market(int[] salesMarket, String marketName) {
        SALES_MARKET = salesMarket;
        MARKET_NAME = marketName;
    }

    @Override
    public String call() {

        Arrays.stream(SALES_MARKET).forEach((price)->revenue.add(price));

        return MARKET_NAME + ": " + revenue.sum();
    }
}
