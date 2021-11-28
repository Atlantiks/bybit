import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Trader {
    static final double percentage = 0.985;
    static final double initialDepo = 2000;
    static double averageDepo = 0;
    static double depo = 2000;
    static double[] takeProfitAt = new double[5];
    static Double[] tradeResults;
    static Trend btcusd;
    static Randoms rnd;
    static Position pos;
    static Strategies strats;

    public static void main(String[] args) {
        btcusd = new Trend();
        rnd = new Randoms();
        strats = new Strategies();

        for (int i = 0; i < 25_000_000; i++) {
            generateTakeProfit();
            trade();
            if (i % 10_000 == 0 )System.out.println(i);
        }
        btcusd.closeConnectionToDB();

    }

    public static void generateTakeProfit() {
        for (int i = 0; i < takeProfitAt.length; i++) {
            takeProfitAt[i] = Math.round(0.001 + (1 + Math.random() / 6) * 10000d) / 10000d;
        }
    }

    public static void trade() {
        int entries = btcusd.hourTrend.length;
        double entryPrice, low;
        averageDepo = 0;
        tradeResults = new Double[50];
        CachedPositions cachPos = new CachedPositions();

        for (int k = 1999; k >= 0; k--) {
            depo = initialDepo;
            Position.ordersCount = 0;
            Position.marginCallsNum.clear();

            //for (int i = rnd.randomNums[750]; i < entries; i++) {
            for (int i = rnd.randomNums[k]; i < entries; i++) {
                entryPrice = btcusd.hourTrend[i][1] * percentage;
                low = btcusd.hourTrend[i][3];

                if (low < entryPrice) {

                    pos = new Position(entryPrice, depo * 0.0625, depo, btcusd.hourTrend);
                    if (k > 50 && cachPos.isCached(i)) {
                        break;
                    } else {
                        cachPos.activateRecording(depo,i);
                    }

                    if (pos.isPositionLiquidated(i)) return;

                    for (int j = i + 1; j < entries; j++) {
                        if (pos.isPositionLiquidated(j)) return;
                        if (j == entries - 1) {
                            pos.closePosition(false);
                            i = j;
                        } else if (pos.canPositionBeClosed(j)) {
                            pos.closePosition(true);
                            i = j;
                            break;
                        }
                    }
                }
            }
            if (pos != null) {
                pos.closePosition(false);
                if (k < 50) tradeResults[k] = pos.depo;
                if (k == 0 && strats.addToStrategiesList(getAverageDeposit()))
                    btcusd.uploadResults(initialDepo,getAverageDeposit(),Position.ordersCount,Position.marginCallsNum,takeProfitAt);
            } else {
                System.out.println("Открытия позиции не произошло");
            }
        }
    }

    public static double getAverageDeposit() {
        return Stream.of(tradeResults).reduce(0.0, Double::sum) / tradeResults.length;
    }
}
