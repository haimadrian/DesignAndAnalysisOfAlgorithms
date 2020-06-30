package algorithm;

import util.ActionResult;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author Haim Adrian
 * @since 28-Jun-20
 */
public class RepeatedSquares implements AlgorithmIfc {

    @Override
    public void execute() {
        // Arrange
        int[] witnesses;
        int num = readInt("Enter a number to run the method of repeated squares on: (for example: 1105 or 83)");
        boolean useRandomWitnesses = readYesNo("Should we generate random witnesses? (y/n) - n means that you specify the witnesses");

        if (!useRandomWitnesses) {
            int witnessesCount = readInt("How many witnesses would you like to enter?");
            witnesses = new int[witnessesCount];

            System.out.println("Enter " + witnessesCount + " witnesses. (between 2 to " + (num-1) + " inclusive)");
            for (int i = 0; i < witnessesCount; i++) {
                witnesses[i] = readInt("");
            }
        } else {
            SecureRandom rand = new SecureRandom();
            int witnessesCount = 4;
            witnesses = new int[witnessesCount];

            // Get random numbers between 2 to num-1
            for (int i = 0; i < witnessesCount; i++) {
                witnesses[i] = rand.nextInt(num - 2) + 2;
            }
        }

        for (int i = 0; i < witnesses.length; i++) {
            repeatedSquaresMethod(witnesses[i], num);
        }
        System.out.println();
    }

    private ActionResult<Long> repeatedSquaresMethod(int witness, int num) {
        // Let n-1=(2^t)u, where t>=1 and u is odd
        int t = 1, u = num - 1;
        while (((u = u/2) % 2) == 0) {
            t++;
        }

        System.out.println("Let n-1=(2^t)u, where t>=1 and u is odd:  t=" + t + ", u=" + u + ", n-1=" + (num - 1));

        ActionResult<Long> modPowerResult = modPow(witness, u, num, true);
        return modPowerResult;
    }

    /**
     * Calculate (a^b)mod(n) using small registers
     * @param a
     * @param b
     * @param n
     * @return
     */
    public static ActionResult<Long> modPow(long a, long b, long n, boolean printData) {
        ActionResult<Long> result = new ActionResult<>();

        // Get binary bits
        String binary = Long.toBinaryString(b);
        int amountOfBits = binary.length();
        boolean[] bits = new boolean[amountOfBits];
        long[] repeatedSquares = new long[amountOfBits];

        bits[0] = binary.charAt(amountOfBits - 1) == '1';
        repeatedSquares[0] = a % n; //c0
        long res = (bits[0] ? repeatedSquares[0] % n : 1);
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

        result.setResult(Long.valueOf(res));
        return result;
    }
}

