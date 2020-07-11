package algorithm;

/**
 * @author Haim Adrian
 * @since 10-Jul-20
 */
public class What implements AlgorithmIfc {
    private static int RADIX = 2;

    private static String getRadixName() {
        return RADIX == 2 ? "Binary" : (RADIX == 8 ? "Octal" : (RADIX == 16 ? "Hexadecimal" : "Decimal"));
    }

    @Override
    public void execute() {
        int x, y;
        RADIX = readInt("Please enter radix. (for example: 2 means Binary, 8 means Octal, 16 means Hexadecimal)");
        if (readYesNo("Would you like to enter x and y as " + getRadixName() + " numbers? (y/n - n means entering Decimal integers)")) {
            String xBin = readLine("Enter x as a binary string. (for example: " + Integer.toUnsignedString(9, RADIX) + ")");
            String yBin = readLine("Enter y as a binary string. (for example: " + Integer.toUnsignedString(12, RADIX) + ")");

            x = Integer.parseInt(xBin, RADIX);
            y = Integer.parseInt(yBin, RADIX);
        } else {
            x = readInt("Enter x as an integer number. (for example: 9)");
            y = readInt("Enter y as a binary string. (for example: 12)");
        }

        StringBuilder out = new StringBuilder();
        String depth = "";
        int result = what(x, y, depth, out);

        System.out.println(out.toString());
        System.out.println("Result is: " + result);
        System.out.println("Result in " + getRadixName() + " is: " + Integer.toUnsignedString(result, RADIX));
    }

    private int what(int x, int y, String depth, StringBuilder out) {
        if ((x == 1) && (y == 1)) {
            return 1;
        }

        if ((x == 0) || (y == 0)) {
            return 0;
        }

        // Make sure amount of radix digits is even.
        StringBuilder xBin = new StringBuilder(Integer.toUnsignedString(x, RADIX));
        if (xBin.length() % 2 != 0) {
            xBin.insert(0, "0");
        }
        StringBuilder yBin = new StringBuilder(Integer.toUnsignedString(y, RADIX));
        if (yBin.length() % 2 != 0) {
            yBin.insert(0, "0");
        }

        // Now make sure both x and y are of the same amount of digits n.
        int xDigits = xBin.length();
        int yDigits = yBin.length();
        int n = Math.max(xDigits, yDigits);
        for (int i = xDigits; i < n; i++) {
            xBin.insert(0, "0");
            xDigits++;
        }
        for (int i = yDigits; i < n; i++) {
            yBin.insert(0, "0");
            yDigits++;
        }

        String x1 = xBin.substring(0, xDigits / 2);
        x1 = x1.isEmpty() ? "0" : x1;
        String x0 = xBin.substring(xDigits / 2);
        x0 = x0.isEmpty() ? "0" : x0;

        String y1 = yBin.substring(0, yDigits / 2);
        y1 = y1.isEmpty() ? "0" : y1;
        String y0 = yBin.substring(yDigits / 2);
        y0 = y0.isEmpty() ? "0" : y0;

        int x1Int = Integer.parseInt(x1, RADIX);
        int x0Int = Integer.parseInt(x0, RADIX);
        int y1Int = Integer.parseInt(y1, RADIX);
        int y0Int = Integer.parseInt(y0, RADIX);

        int x1PlusX0 = x1Int + x0Int;
        int y1PlusY0 = y1Int + y0Int;

        out.append(depth).append("What(").append(x).append(", ").append(y).append(")").append(System.lineSeparator());
        out.append(depth).append("x1=").append(x1).append(", x0=").append(x0).append(System.lineSeparator());
        out.append(depth).append("y1=").append(y1).append(", y0=").append(y0).append(System.lineSeparator());
        out.append(depth).append("P=What(").append(x1PlusX0).append(", ").append(y1PlusY0).append(")").append(System.lineSeparator());
        int P = what(x1PlusX0, y1PlusY0, depth + "  ", out);
        out.append(depth).append("P=").append(P).append(System.lineSeparator());

        out.append(depth).append("Q=What(").append(x1Int).append(", ").append(y1Int).append(")").append(System.lineSeparator());
        int Q = what(x1Int, y1Int, depth + "  ", out);
        out.append(depth).append("Q=").append(Q).append(System.lineSeparator());

        out.append(depth).append("R=What(").append(x0Int).append(", ").append(y0Int).append(")").append(System.lineSeparator());
        int R = what(x0Int, y0Int, depth + "  ", out);
        out.append(depth).append("R=").append(R).append(System.lineSeparator());

        int twoPowN = (int)Math.pow(2, n);
        int twoPowHalfN = (int)Math.pow(2, (int) (n / 2));
        int result = (Q * twoPowN) + ((P - Q - R) * twoPowHalfN) + R;
        out.append(depth).append("return (").append(Q).append("*").append(twoPowN).append(" + ")
           .append(P-Q-R).append("*").append(twoPowHalfN).append(" + ").append(R).append(") = ").append(result).append(System.lineSeparator());

        return result;
    }
}

