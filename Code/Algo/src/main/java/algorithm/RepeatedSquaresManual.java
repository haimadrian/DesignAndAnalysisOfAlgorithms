package algorithm;

/**
 * @author Haim Adrian
 * @since 01-Jul-20
 */
public class RepeatedSquaresManual implements AlgorithmIfc {

    @Override
    public void execute() {
        System.out.println("We are about to calculate a^b(modn) using the method of repeated squares.");
        long a = readInt("Enter a:");
        long b = readInt("Enter b:");
        long n = readInt("Enter n:");

        RepeatedSquares.modPow(a, b, n, true);
    }
}

