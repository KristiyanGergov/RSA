import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
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
        String fileName = "";

        List<Boolean> allRequiredArgsProvided = Arrays.asList(false, false, false);

        for (int i = 0; i < args.length; i++) {
            String argKey = args[i];

            if (argKey.equals("-p") || argKey.equals("-precision")) {
                precision = Integer.parseInt(args[i + 1]);
                allRequiredArgsProvided.set(0, true);
            }
            if (argKey.equals("-t") || argKey.equals("-threads")) {
                threadsNumber = Integer.parseInt(args[i + 1]);
                allRequiredArgsProvided.set(1, true);
            }
            if (argKey.equals("-o")) {
                fileName = args[i + 1];
                allRequiredArgsProvided.set(2, true);
            }
            if (argKey.equals("-q")) {
                quiet = true;
            }
        }
        if (precision <= 0 || threadsNumber <= 0 || fileName.isBlank() || allRequiredArgsProvided.contains(false)) {
            System.err.println(
                    "Some of the args are not correct: Example valid input: \n" +
                    "java Pi \"-p\" (required) \"10\" \"-t\" (required) \"5\" \"-o\" (required) \"output.txt\" -q (optional)"
            );
            return;
        }

        try (PrintStream out = new PrintStream(new FileOutputStream(fileName))) {
            System.setOut(out);

            PiThread[] threads = new PiThread[threadsNumber];

            List<Pair<Integer, Integer>> pairs = calculatePairs(precision, threadsNumber);

            for (int t = 0; t < threadsNumber; t++) {

                PiThread thread = new PiThread("Thread " + (t + 1), quiet, pairs.get(t));
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
