import java.util.HashMap;
import java.util.Map;

public class CachedPositions {
    Map<Integer,Double> cachedPos;
    double initialDeposit;
    double finalDeposit;
    int startTime;

    CachedPositions() {
        cachedPos = new HashMap<>();
    }

    boolean isCached(int time) {
        return cachedPos.containsKey(time);
    }

    void activateRecording(double positionStartDeposit, int time) {
        initialDeposit = positionStartDeposit;
        startTime = time;
        cachedPos.put(time,0.0);
    }

    void completeRecording(double positionEndDeposit) {
        finalDeposit = positionEndDeposit;
        double coff = finalDeposit / initialDeposit;
        cachedPos.put(startTime,coff);
    }


}
