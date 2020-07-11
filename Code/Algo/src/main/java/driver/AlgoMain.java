package driver;

import algorithm.Algorithm;

import java.util.Scanner;

/**
 * @author Haim Adrian
 * @since 26-Jun-20
 */
public class AlgoMain {
    public static void main(String[] args) {
        // In order to debug or modify the input of a specific algorithm, refer to its class.
        // The algorithms can be found under "algorithm" package. You can see a list of them
        // with reference to their classes at the Algorithm enum. (Ctrl+n for intellij, Ctrl+T for eclipse -> Algorithm)
        new AlgoMain().run(args);
    }

    @SuppressWarnings("unused")
    public void run(String[] args) {
        System.out.println("Design and Analysis of Algorithms");
        System.out.println("Based on Dr Ayelet Butman presentations. HIT 2020");
        System.out.println("01/07/2020 - The date of the big fucking surprise.");
        System.out.println("-----------------------------------------------------------------" + System.lineSeparator());
        System.out.println("Follow the instructions of each algorithm carefully!" + System.lineSeparator() +
                           "Don't paste set of values and don't try to enter illegal values." + System.lineSeparator() +
                           "When you are being asked to enter several values one by another," + System.lineSeparator() +
                           "it means you should enter one number, e.g. 10, then press enter" + System.lineSeparator() +
                           "and only after pressing enter you can enter the next value." + System.lineSeparator() +
                           "This means that the result of entering, for example, 10 11 in" + System.lineSeparator() +
                           "a single line is unknown." + System.lineSeparator() +
                           "Note that this is a free to use utility. You have no one to blame in case of bugs." + System.lineSeparator() +
                           "GL & HF" + System.lineSeparator());

        Algorithm userChoice;
        while ((userChoice = printMenuAndGetUserChoice()) != Algorithm.exit) {
            if (userChoice == Algorithm.unknown) {
                System.out.println("Unknown choice, please try again carpenter..");
            } else {
                try {
                    System.out.println("================================ " + userChoice.getTitle() + " ================================");
                    userChoice.newInstance().execute();
                } catch (Exception e) {
                    System.err.println("Something went wrong while trying to execute the " + userChoice.name() + " algorithm.");
                    e.printStackTrace();
                } finally {
                    System.out.println("=============================================================================================");
                }
            }

            System.out.println(System.lineSeparator());
        }

        System.out.println("Good bye buddy!");
    }

    private Algorithm printMenuAndGetUserChoice() {
        Algorithm userChoice;

        StringBuilder menu = new StringBuilder("Select an algorithm to execute");
        for (Algorithm algo : Algorithm.values()) {
            if (algo.getTitle() != null) {
                menu.append(System.lineSeparator()).append(algo.ordinal()).append(". ").append(algo.getTitle());
            }
        }

        System.out.println(menu.toString());

        @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
        Scanner console = new Scanner(System.in);
        try {
            int choice = console.nextInt();
            userChoice = Algorithm.values()[choice];
        } catch (Throwable ignore) {
            userChoice = Algorithm.unknown;
        }

        return userChoice;
    }
}

