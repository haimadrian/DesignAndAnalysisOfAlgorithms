package algorithm;

import util.ActionResult;

import java.util.Arrays;

/**
 * @author Haim Adrian
 * @since 28-Jun-20
 */
public class RepeatedSquares implements AlgorithmIfc {

    @Override
    public void execute() {
        // Arrange
        int[] witnesses = new int[] { 2, 6, 8, 12, 34 };
        int[] numbers = new int[] { 83, 1105, 1015 };

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < witnesses.length; j++) {
                repeatedSquaresMethod(witnesses[j], numbers[i]);
            }
            System.out.println();
        }
    }

    private ActionResult<Integer> repeatedSquaresMethod(int witness, int num) {
        // Let n-1=(2^t)u, where t>=1 and u is odd
        int t = 1, u = num - 1;
        while (((u = u/2) % 2) == 0) {
            t++;
        }

        System.out.println("Let n-1=(2^t)u, where t>=1 and u is odd:  t=" + t + ", u=" + u + ", n-1=" + (num - 1));

        ActionResult<Integer> modPowerResult = modPow(witness, u, num, true);
        return modPowerResult;
    }

    /**
     * Calculate (a^b)mod(n) using small registers
     * @param a
     * @param b
     * @param n
     * @return
     */
    public static ActionResult<Integer> modPow(long a, long b, long n, boolean printData) {
        ActionResult<Integer> result = new ActionResult<>();

        // Get binary bits
        String binary = Long.toBinaryString(b);
        int amountOfBits = binary.length();
        boolean[] bits = new boolean[amountOfBits];
        long[] repeatedSquares = new long[amountOfBits];

        bits[0] = binary.charAt(amountOfBits - 1) == '1';
        repeatedSquares[0] = a % n; //c0
        long res = repeatedSquares[0] % n;
        for (int i = 1; i < amountOfBits; i++) {
            // Multiply operation costs amountOfBits^2
            result.countSteps(amountOfBits*amountOfBits);

            repeatedSquares[i] = ((repeatedSquares[i-1] * repeatedSquares[i-1]) % n);
            bits[i] = binary.charAt(amountOfBits - 1 - i) == '1';

            if (bits[i]) {
                res = ((res * repeatedSquares[i]) % n);
            }
        }

        if (printData) {
            System.out.println("(" + a + "^" + b + ") mod(" + n + "): ");
            System.out.println("b in binary: " + binary);
            System.out.println("C series: " + Arrays.toString(repeatedSquares));
            System.out.println("Result: " + res + ".    Steps took: " + result.getStepsCount() + " - O(k^3) where k is num of bits");
        }

        result.setResult(Integer.valueOf((int) res));
        return result;
    }
}

