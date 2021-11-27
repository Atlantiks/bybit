import java.util.Arrays;

public class Randoms {
    int[] randomNums = new int[1000];

    Randoms() {
        System.out.println("Запуск генераторов случайных чисел");
        generateRandomEntryPoints(11800, 25500);
        Arrays.sort(randomNums);
        System.out.println(Arrays.toString(randomNums));
    }

    private void generateRandomEntryPoints(int beginDate, int endDate) {
        for (int i = 0; i < randomNums.length; i++) {
            boolean hasRepeats = false;
            do {
                randomNums[i] = beginDate + (int) (Math.random() * (endDate - beginDate));
                for (int j = 0; j < i; j++) {
                    if (randomNums[j] == randomNums[i]) {
                        hasRepeats = true;
                        break;
                    } else {
                        hasRepeats = false;
                    }
                }
            } while (hasRepeats) ;
        }
    }
}
