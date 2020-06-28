package algorithm;

import util.ActionResult;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Haim Adrian
 * @since 27-Jun-20
 */
public class MillerRabin implements AlgorithmIfc {

    private static final String COMPOSITE = "COMPOSITE";
    private static final String PRIME = "PRIME";
    private static final Random RAND = new SecureRandom();
    private boolean shouldPrintUandT = true;

    @Override
    public void execute() {
        // Arrange
        int num = 1105;
        int tries = 5;
        StringBuilder primes = new StringBuilder();

        // Act
        ActionResult<String> result = millerRabin(num, tries);

        // Now, after finished the miller rabin execution, turn off the "Let n-1=(2^t)u" message
        shouldPrintUandT = false;

        // Get all prime numbers between 0 to 1000
        int count = 0;
        for (int i = 0; i < 10; i++) {
            primes.append(i*100).append("-").append(i*100 + 99).append(": ");
            for (int j = 0; j < 100; j++) {
                int n = i*100 + j;
                if (millerRabin(n, 10).getResult().equals(PRIME)) {
                    count++;

                    primes.append(n).append(", ");
                }
            }
            primes.delete(primes.length() - 2, primes.length());
            primes.append(System.lineSeparator());
        }

        // Output
        System.out.println("Number: " + num);
        System.out.println("Tries: " + tries);
        System.out.println("Miller-Rabin result: " + result.getResult());
        System.out.println("It took " + result.getStepsCount() + " steps to execute the algorithm. (O(k^3) where k is the amount of bits of num) ");
        System.out.println("All primes below 1000: (" + count + " numbers)");
        System.out.println(primes.toString());
    }

    /**
     * Check if a given number is composite or prime.
     * If number is composite, than the result is 100% true. Otherwise, there is a very small probability to get a wrong result
     * @param num the number to check if composite or prime
     * @param probabilityTries How many times we want to try checking if num is a prime number. - Monte Carlo
     * @return A result containing "PRIME" or "COMPOSITE".
     */
    private ActionResult<String> millerRabin(int num, int probabilityTries) {
        ActionResult<String> result = new ActionResult<>(COMPOSITE);

        // Edge cases
        if (num == 2) {
            result.setResult(PRIME);
            return result;
        }
        if ((num < 2) || (num % 2 == 0)) {
            return result;
        }

        for (int i = 0; i < probabilityTries; i++) {
            // Get a random number between 1 to num-1
            int a = RAND.nextInt(num - 2) + 2;

            ActionResult<Boolean> witnessResult = witness(a, num);

            // Count the steps of witness
            result.add(witnessResult);

            if (witnessResult.getResult().booleanValue()) {
                return result;
            }
        }

        result.setResult(PRIME);
        return result;
    }

    /**
     * Checks if the specified candidate is a witness for a decomposable of num.
     * @param candidate The candidate to use
     * @param num the number to check
     * @return Whether candidate is a witness or not
     */
    private ActionResult<Boolean> witness(int candidate, int num) {
        ActionResult<Boolean> result = new ActionResult<>(Boolean.FALSE);

        // Let n-1=(2^t)u, where t>=1 and u is odd
        int t = 1, u = num - 1;
        while (((u = u/2) % 2) == 0) {
            t++;
        }

        if (shouldPrintUandT) {
            System.out.println("Let n-1=(2^t)u, where t>=1 and u is odd:  t=" + t + ", u=" + u + ", n-1=" + (num - 1));
        }

        ActionResult<Integer> modPowerResult = RepeatedSquares.modPow(candidate, u, num, false);

        // Count that amount of steps occur because of the multiplication: k^2, which happens
        // in a loop until t, where t is also a k bits number. Witness complexity: k^3
        result.add(modPowerResult);

        double lastMod = modPowerResult.getResult().intValue(), currMod;
        for (int i = 0; i < t; i++) {
            currMod = (lastMod*lastMod) % num;

            // Detection of non trivial square root
            if ((currMod == 1) && (lastMod != 1) && (lastMod != num - 1)) {
                result.setResult(Boolean.TRUE);
                return result;
            }

            lastMod = currMod;
        }

        // num does not belong to Fermat's little theorem. (candidate^(num-1)===1mod(num))
        if (lastMod != 1) {
            result.setResult(Boolean.TRUE);
            return result;
        }

        return result;
    }
}
