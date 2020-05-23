import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MyThread extends Thread {
    String nameThread;
    boolean quit;
    BigDecimal sum = new BigDecimal(0);
    Pair<Integer, Integer> pair;

    public MyThread(String nameThread, boolean quit, Pair<Integer, Integer> pair) {
        this.nameThread = nameThread;
        this.quit = quit;
        this.pair = pair;
    }

    private BigInteger factorial(int number) {
        BigInteger fact = BigInteger.valueOf(1);
        for (int i = 1; i <= number; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    private BigDecimal calculate(int n) {

        BigInteger topPart = factorial(4 * n).multiply(BigInteger.valueOf(1123 + (21460 * n)));
        double bottomPart = ((Math.pow(factorial(n) * Math.pow(4, n), 4) * Math.pow(882, (2 * n))));

        BigDecimal result = new BigDecimal(0);
        try {
            result = BigDecimal.valueOf(topPart.divide(bottomPart));
        } catch (NumberFormatException e) {
            System.out.println("Top part: " + topPart);
            System.out.println("Bottom part: " + bottomPart);
            throw e;
        }

        if (n % 2 == 1) {
            result = result.negate();
        }

        return result;
    }

    public void run() {
        if (!quit) {
            System.out.println(nameThread + " is starting! " + "Points!");
        }
        for (int i = pair.getKey(); i < pair.getValue(); i++) {
            sum = sum.add(calculate(i));
        }
        if (!quit) {
            System.out.println(nameThread + " is finishing! Points in circle!");
        }
    }
}
