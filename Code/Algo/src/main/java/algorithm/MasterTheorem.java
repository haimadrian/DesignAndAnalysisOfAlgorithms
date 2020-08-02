package algorithm;

/**
 * @author Haim Adrian
 * @since 28-Jun-20
 */
public class MasterTheorem implements AlgorithmIfc {

    @Override
    public void execute() {
        double a, b, m, k;

        System.out.println("T(n) = aT(n/b) + \u03F4((n^m) * (logn)^k)");
        System.out.println("Note that each parameter is of type double, so you can enter fraction");

        a = readDouble("Enter a:");
        b = readDouble("Enter b:");
        m = readDouble("Enter m: (0 will result in n^0 which is 1)");
        k = readDouble("Enter k: (0 will result in (logn)^0 which is 1)");

        masterTheorem(a, b, m, k);
        System.out.println();
        System.out.println("Be careful of Ayelet tricks! If you encounter capital/small O or Omega," + System.lineSeparator() +
                           "go to the main menu and press 9 for Big O notation and order of magnitude reminder.");
    }

    private void masterTheorem(double a, double b, double m, double k) {
        // Convert to log2
        double log = Math.log(a) / Math.log(b);
        String nPowerLog = functionToString("n", log);
        if (doubleEquals(m, log)) {
            if (k != 0) {
                System.out.println("Master Theorem - Case 2 - Extended - \u03F4(n^logb(a) * (logn)^(k+1)): n^logb(a)=" + nPowerLog);
                System.out.println("T(n) = \u03F4(" + nPowerLog + " * " + functionToString("(logn)", k + 1) + ")");
            } else {
                System.out.println("Master Theorem - Case 2 - \u03F4(n^logb(a) * logn): n^logb(a)=" + nPowerLog);
                System.out.println("T(n) = \u03F4(" + nPowerLog + " * (logn))");
            }
        } else if (log > m) {
            // f(n) = O(n^logb(a))
            System.out.println("Master Theorem - Case 1 - \u03F4(n^logb(a)): n^logb(a)=" + nPowerLog);
            System.out.println("T(n) = \u03F4(" + nPowerLog + ")");
        } else {
            String func = functionToString("n", m);
            if (k != 0) {
                if (k == 1) {
                    func += " * (logn)";
                } else {
                    func += " * " + functionToString("(logn)", k);
                }
            }

            System.out.println("Master Theorem - Case 3 - \u03F4(f(n)): n^logb(a)=" + nPowerLog);
            System.out.println("T(n) = \u03F4(" + func + ")");
        }
    }

    private static String functionToString(String func, double power) {
        if (doubleEquals(power, 0)) {
            return "1";
        } else if (doubleEquals(power, 1)) {
            return func;
        } else if (doubleEquals(power % 1.0, 0)) {
            return func + "^" + ((int)power);
        } else {
            return func + "^" + power;
        }
    }

    private static boolean doubleEquals(double x, double y) {
        return Math.abs(x - y) < 1e-9;
    }
}

