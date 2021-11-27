import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Strategies {
    List<Double> deposits = new ArrayList<>(51);
    int pos;

    Strategies() {
        pos = -1;
    }

    boolean addToStrategiesList(double finalDepo) {
        if (pos < 50) {
            deposits.add(++pos,finalDepo);
            deposits.sort(Comparator.naturalOrder());
            return true;
        } else {
            for (int i = 0; i < pos; i++) {
                if (finalDepo > deposits.get(i)) {
                    deposits.add(i,finalDepo);
                    deposits.remove(50);
                    return true;
                }
            }
        }
        return false;
    }




}
