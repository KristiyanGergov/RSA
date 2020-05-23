import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pi {

    static int numThread;
    static String threadName;
    static boolean quit = false;
    static MyThread[] array;

    public static void main(String[] args) {
        String[] list = new String[]{
                "-p",
                "10240",
                "-t",
                "3"
        };

        main2(list);
    }

    private static List<Pair<Integer, Integer>> calculatePairs(int precision, int numberOfThreads) {
        int num = precision / numberOfThreads;

        List<Pair<Integer, Integer>> pairs = new ArrayList<>();

        for (int i = 0; i < numberOfThreads - 1; i++) {
            pairs.add(new Pair<>(num * i, num * i + num));
        }
        pairs.add(new Pair<>(num * (numberOfThreads - 1), precision));

        return pairs;
    }

    public static void main2(String[] args) {
        long timeOfStart = Calendar.getInstance().getTimeInMillis();

        int precision = 0;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-p")) {
                precision = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-t")) {
                numThread = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-q")) {
                quit = true;
            }
        }
        if (precision <= 0 || numThread <= 0 || args.length < 4 || args.length > 5) {
            System.out.println("ERROR: Args are not correct!!!");
            return;
        }

        array = new MyThread[numThread];

        List<Pair<Integer, Integer>> pairs = calculatePairs(precision, numThread);

        for (int t = 0; t < numThread; t++) {

            MyThread thread = new MyThread("Thread " + t, quit, pairs.get(t));
            thread.start();
            array[t] = thread;
        }

        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < numThread; i++) {
            try {
                array[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sum = sum.add(array[i].sum);
        }

        double pi = sum.divide(BigDecimal.valueOf(882 / 4), RoundingMode.CEILING).doubleValue();

        long timeOfEnd = Calendar.getInstance().getTimeInMillis();
        if (!quit) {
            System.out.println("Points in circle: " + sum);
        }
        System.out.println("Time of calculate Pi: " + (timeOfEnd - timeOfStart) + " millis");
        System.out.println("Calculate Pi: " + pi);

    }
}
