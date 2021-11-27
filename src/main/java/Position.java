import java.util.HashMap;
import java.util.Map;

public class Position {
    static int ordersCount = 0;
    static Map<Integer,Integer> marginCallsNum = new HashMap<>();
    double[][] btcTrend;
    double entryPrice;
    double position;
    double depo;
    int marginCalls;
    final int maxMarginCalls = 4;

    Position(double entryPrice, double position, double depo, double[][] btcTrend) {
        this.btcTrend = btcTrend;
        this.entryPrice = entryPrice;
        this.position = position;
        this.depo = depo;
        openPosition();
    }

    private void openPosition() {
        marginCalls = 0;
        ordersCount++;
        depo -= position;
    }

    boolean isPositionLiquidated(int hour) {
        double low = btcTrend[hour][3];

        if (low < entryPrice * 0.92) {
            while (low < entryPrice * 0.92) {
                doubleUpPosition();
                marginCalling();
            }
        }
        return marginCalls > maxMarginCalls;
    }

    void doubleUpPosition() {
        depo += position;
        position *= 2;
        depo -= position;
        entryPrice *= 0.96;
    }

    void marginCalling() {
        marginCalls++;
        marginCallsNum.merge(marginCalls,1,(oldVal, newVal) -> oldVal + 1);
        if (marginCalls > 1)
            marginCallsNum.merge(marginCalls - 1, 0, (oldVal, newVal) -> oldVal - 1);
    }

    boolean canPositionBeClosed(int hour) {
        double high = btcTrend[hour][2];

        return high >= entryPrice * Trader.takeProfitAt[this.marginCalls];
    }

    void closePosition(boolean gainProfit) {
        if (gainProfit) {
            double coff = 1 + ((Trader.takeProfitAt[this.marginCalls] - 1) * 10);
            this.position *= coff;
        }
        this.depo += this.position;
        Trader.depo = this.depo;
        this.position = 0;
    }


}
