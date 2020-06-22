import java.math.BigDecimal;
import java.util.Calendar;

public class PiThread extends Thread {
    BigDecimal sum = new BigDecimal(0);
    private String nameThread;
    private boolean quit;
    private Pair<Integer, Integer> firstPair;
    private Pair<Integer, Integer> secondPair;

    public PiThread(String nameThread, boolean quit, Pair<Integer, Integer> firstPair, Pair<Integer, Integer> secondPair) {
        this.nameThread = nameThread;
        this.quit = quit;
        this.firstPair = firstPair;
        this.secondPair = secondPair;
    }

    public void run() {
        long timeOfStart = Calendar.getInstance().getTimeInMillis();

        if (!quit) {
            System.out.println(nameThread + " is starting!");
        }
        for (int i = firstPair.key; i < firstPair.value; i++) {
            sum = sum.add(MathUtils.calculatePi(i));
        }
        for (int i = secondPair.key; i < secondPair.value; i++) {
            sum = sum.add(MathUtils.calculatePi(i));
        }
        if (!quit) {
            long timeOfEnd = Calendar.getInstance().getTimeInMillis();

            System.out.println(nameThread + " is finishing! Time needed for thread \"" + nameThread + "\" to finish: " + (timeOfEnd - timeOfStart) + " millis");
        }
    }
}
