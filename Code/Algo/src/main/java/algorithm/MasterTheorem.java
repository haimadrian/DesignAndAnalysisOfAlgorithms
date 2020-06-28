package algorithm;

/**
 * @author Haim Adrian
 * @since 28-Jun-20
 */
public class MasterTheorem implements AlgorithmIfc {

    @Override
    public void execute() {
        double a, b, m, k;

        System.out.println("T(n) = aT(n/b) + O((n^m) * (logn)^k)");
        System.out.println("Note that each parameter is of type double, so you can enter fraction");
        a = readDouble("Enter a:");
        b = readDouble("Enter b:");
        m = readDouble("Enter m: (0 will result in n^0 which is 1)");
        k = readDouble("Enter k: (0 will result in (logn)^0 which is 1)");

        masterTheorem(a, b, m, k);
    }

    private void masterTheorem(double a, double b, double m, double k) {
        // Convert to log2
        double log = Math.log(a) / Math.log(b);
        if (doubleEquals(m, log)) {
            if (k != 0) {
                System.out.println("Master Theorem - Case 2 - Extended - O(n^logb(a) * (logn)^(k+1)): n^logb(a)=n^" + log);
                System.out.println("T(n) = O(n^" + log + " * (logn)^" + (k + 1) + ")");
            } else {
                System.out.println("Master Theorem - Case 2 - O(n^logb(a) * logn): n^logb(a)=n^" + log);
                System.out.println("T(n) = O(n^" + log + " * (logn))");
            }
        } else if (log > m) {
            // f(n) = O(n^logb(a))
            System.out.println("Master Theorem - Case 1 - O(n^logb(a)): n^logb(a)=n^" + log);
            System.out.println("T(n) = O(n^" + log + ")");
        } else {
            System.out.println("Master Theorem - Case 3 - O(f(n)): n^logb(a)=n^" + log);
            System.out.println("T(n) = O(n^" + m + ")");
        }
    }

    private boolean doubleEquals(double x, double y) {
        return Math.abs(x - y) < 1e-9;
    }
}

