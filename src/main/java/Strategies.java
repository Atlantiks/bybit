import java.util.*;

public class Strategies {
    NavigableSet<Double> results = new TreeSet<>();
    int pos;

    Strategies() {
        pos = -1;
    }

    boolean addToStrategiesList(double finalDepo) {
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
