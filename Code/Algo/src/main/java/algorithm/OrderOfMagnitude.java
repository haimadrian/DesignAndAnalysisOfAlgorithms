package algorithm;

/**
 * @author Haim Adrian
 * @since 10-Jul-20
 */
public class OrderOfMagnitude implements AlgorithmIfc {

    @Override
    public void execute() {
        System.out.println("The order of known magnitudes is:");
        System.out.println("---------------------------------");
        System.out.println("C  <  log(logn)  <  logn  <  (logn)^2  <  \u221An  <  n  <  nlogn  <  n^2  <  2^n  <  n!  <  n^n");
        System.out.println("Notes:");
        System.out.println("1. C is a constant.");
        System.out.println("2. log(n!) = nlogn.");
        System.out.println("3. You must be familiar with logarithmic rules. e.g. log(n^n) = nlogn,  2^(logn) = n,  etc.");
        System.out.println();
        System.out.println("Big O notation:");
        System.out.println("---------------");
        System.out.println("Capital O:\t\tf(n) = \u004F(g(n))  -  for each n >= n0 > 0 and a constant C > 0 :\t\t\tf(n) <= C*g(n)");
        System.out.println("Small O:\t\tf(n) = \u006F(g(n))  -  for each n >= n0 > 0 and for each constant C > 0 :\tf(n) < C*g(n)");
        System.out.println("Capital Omega:\tf(n) = \u03A9(g(n))  -  for each n >= n0 > 0 and a constant C > 0 :\t\t\tf(n) >= C*g(n)");
        System.out.println("Small Omega:\tf(n) = \u03C9(g(n))  -  for each n >= n0 > 0 and for each constant C > 0 :\tf(n) > C*g(n)");
        System.out.println("Theta:\t\t\tf(n) = \u03F4(g(n))  -  for each n >= n0 > 0 and for constants C1, C2 > 0 :\tC1*g(n) <= f(n) <= C2*g(n)");
    }
}

