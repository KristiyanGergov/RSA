import javafx.util.Pair;

import java.math.BigDecimal;

public class MyThread extends Thread {
    private String nameThread;
    private boolean quit;
    private Pair<Integer, Integer> pair;

    BigDecimal sum = new BigDecimal(0);

    public MyThread(String nameThread, boolean quit, Pair<Integer, Integer> pair) {
        this.nameThread = nameThread;
        this.quit = quit;
        this.pair = pair;
    }

    public void run() {
        if (!quit) {
            System.out.println(nameThread + " is starting!");
        }
        for (int i = pair.getKey(); i < pair.getValue(); i++) {
            sum = sum.add(MathUtils.calculatePi(i));
        }
        if (!quit) {
            System.out.println(nameThread + " is finishing!");
        }
    }
}
