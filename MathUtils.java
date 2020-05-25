import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.valueOf;

public class MathUtils {

    private static BigDecimal factorial(int number) {
        BigDecimal fact = valueOf(1);
        for (int i = 1; i <= number; i++) {
            fact = fact.multiply(valueOf(i));
        }
        return fact;
    }

    public static BigDecimal calculatePi(int n) {

        BigDecimal topPart = factorial(4 * n)
                .multiply(valueOf(21460)
                        .multiply(valueOf(n))
                        .add(valueOf(1123)));

        BigDecimal bottomPart = (
                factorial(n)
                        .multiply(valueOf(4).pow(n))
                        .pow(4))
                .multiply(valueOf(882)
                        .pow(2 * n));

        BigDecimal result = topPart.divide(bottomPart, 10, RoundingMode.HALF_UP);

        if (n % 2 == 1) {
            result = result.negate();
        }

        return result;
    }
}
