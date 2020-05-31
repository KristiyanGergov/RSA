import java.math.BigDecimal;

public class PiThread extends Thread {
    BigDecimal sum = new BigDecimal(0);
    private String nameThread;
    private boolean quit;
    private Pair<Integer, Integer> pair;

    public PiThread(String nameThread, boolean quit, Pair<Integer, Integer> pair) {
        this.nameThread = nameThread;
        this.quit = quit;
        this.pair = pair;
    }

    public void run() {
        if (!quit) {
            System.out.println(nameThread + " is starting!");
        }
        for (int i = pair.key; i < pair.value; i++) {
            sum = sum.add(MathUtils.calculatePi(i));
        }
        if (!quit) {
            System.out.println(nameThread + " is finishing!");
        }
    }
}
