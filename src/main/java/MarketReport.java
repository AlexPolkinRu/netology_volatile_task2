import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Aleksandr Polochkin
 * 30.07.2022
 */

public class MarketReport implements Callable<MarketReport> {

    private final String MARKET_NAME;
    private final int[] MARKET_SALES;

    private long marketRevenue;
    private int numberOfSales;

    LongAdder revenue = new LongAdder();

    public MarketReport(int[] salesMarket, String marketName) {
        MARKET_SALES = salesMarket;
        MARKET_NAME = marketName;
    }

    public long getRevenue() {
        return marketRevenue;
    }

    @Override
    public MarketReport call() {

        Arrays.stream(MARKET_SALES).forEach((sale)->revenue.add(sale));

        numberOfSales = MARKET_SALES.length;
        marketRevenue = revenue.sum();

        return this;
    }

    @Override
    public String toString() {
        return "Название объекта: " + MARKET_NAME + "\n"
                + "Выручка за день: " + marketRevenue + "\n"
                + "Объем продаж: " + numberOfSales;
    }
}
