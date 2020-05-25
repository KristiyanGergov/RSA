import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.math.BigDecimal.valueOf;

public class Pi {

    private static List<Pair<Integer, Integer>> calculatePairs(int precision, int numberOfThreads) {
        int num = precision / numberOfThreads;

        List<Pair<Integer, Integer>> pairs = new ArrayList<>();

        for (int i = 0; i < numberOfThreads - 1; i++) {
            pairs.add(new Pair<>(num * i, num * i + num));
        }
        pairs.add(new Pair<>(num * (numberOfThreads - 1), precision));

        return pairs;
    }

    public static void main(String[] args) {
        long timeOfStart = Calendar.getInstance().getTimeInMillis();

        int precision = 0;
        int threadsNumber = 0;
        boolean quiet = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-p")) {
                precision = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-t")) {
                threadsNumber = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-q")) {
                quiet = true;
            }
        }
        if (precision <= 0 || threadsNumber <= 0 || args.length < 4 || args.length > 5) {
            System.out.println("ERROR: Args are not correct!!!");
            return;
        }

        MyThread[] threads = new MyThread[threadsNumber];

        List<Pair<Integer, Integer>> pairs = calculatePairs(precision, threadsNumber);

        for (int t = 0; t < threadsNumber; t++) {

            MyThread thread = new MyThread("Thread " + t, quiet, pairs.get(t));
            thread.start();
            threads[t] = thread;
        }

        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < threadsNumber; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sum = sum.add(threads[i].sum);
        }

        double pi = sum.divide(valueOf(882 * 4), RoundingMode.CEILING).doubleValue();
        pi = Math.pow(pi, -1);

        long timeOfEnd = Calendar.getInstance().getTimeInMillis();

        System.out.println("Time to calculate Pi: " + (timeOfEnd - timeOfStart) + " millis");
        System.out.println("Calculate Pi: " + pi);
    }
}
