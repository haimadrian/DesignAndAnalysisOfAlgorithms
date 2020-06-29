package algorithm;

import java.util.Scanner;

/**
 * Main interface for any algorithm implementation.<br/>
 * Find the algorithm you want to debug by its class name, under the "algorithm" package. You can use the IDE
 * to locate all implementors of this interface. (Ctrl+H will open type hierarchy window)
 * Another place where you can find a list of all algorithms is the {@link Algorithm} enum.
 *
 * @author Haim Adrian
 * @since 26-Jun-20
 */
public interface AlgorithmIfc {
    void execute();

    default int readInt(String instruction) {
        @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
        Scanner console = new Scanner(System.in);

        if (!instruction.isEmpty()) {
            System.out.println(instruction);
        }
        int result = 0;
        boolean scan = true;
        while (scan) {
            try {
                result = console.nextInt();
                scan = false;
            } catch (Throwable ignore) {
                System.err.println("Illegal input. Try again carpenter");
            }
        }

        return result;
    }

    default double readDouble(String instruction) {
        @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
        Scanner console = new Scanner(System.in);

        if (!instruction.isEmpty()) {
            System.out.println(instruction);
        }
        double result = 0;
        boolean scan = true;
        while (scan) {
            try {
                result = console.nextDouble();
                scan = false;
            } catch (Throwable ignore) {
                System.err.println("Illegal input. Try again carpenter");
            }
        }

        return result;
    }

    default String readLine(String instruction) {
        @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
        Scanner console = new Scanner(System.in);

        if (!instruction.isEmpty()) {
            System.out.println(instruction);
        }
        String result = "";
        boolean scan = true;
        while (scan) {
            try {
                result = console.nextLine();

                if (result.isEmpty()) {
                    System.err.println("Illegal input. Try again carpenter");
                } else {
                    scan = false;
                }
            } catch (Throwable ignore) {
                System.err.println("Illegal input. Try again carpenter");
            }
        }

        return result;
    }

    default boolean readYesNo(String instruction) {
        return readLine(instruction).trim().equalsIgnoreCase("y");
    }
}

