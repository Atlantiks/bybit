import java.util.*;

public class Strategies {
    List<Double> deposits = new ArrayList<>(51);
    NavigableSet<Double> results = new TreeSet<>();
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

    boolean alternativeAdd(double finalDepo) {
        if (pos < 50) {
            pos++;
            results.add(finalDepo);
            return true;
        } else if (results.lower(finalDepo) != null) {
            results.add(finalDepo);
            results.pollFirst();
            return true;
        }
        return false;
    }




}
