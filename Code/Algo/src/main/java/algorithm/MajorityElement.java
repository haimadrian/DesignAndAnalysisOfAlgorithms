package algorithm;

import util.ActionResult;

import java.util.Arrays;

/**
 * First find an element which is suspicious to be the majority element, and then check if it really is.<br/>
 * A majority element is an element which got more than n/2 occurrences in a series.
 * @author Haim Adrian
 * @since 26-Jun-20
 */
public class MajorityElement implements AlgorithmIfc {
    private static final int UNDEFINED = -24;

    @Override
    public void execute() {
        // Arrange
        int[] arr = new int[] { 1, 3, 5, 7, 5, 11, 5, 7, 5, 5 }; // 9 elements, 5 occurs four times (9/2=4)
        int[] arr2 = new int[] { 30, 8, 1995, 30, 8, 1995 }; // No majority

        // Act
        ActionResult<Integer> majorityElement1 = majorityElement(arr);
        ActionResult<Integer> majorityElement2 = majorityElement(arr2);

        // Output
        printResult(arr, majorityElement1);
        printResult(arr2, majorityElement2);
    }

    private ActionResult<Integer> majorityElement(int[] arr) {
        // First, find a candidate - O(n)
        ActionResult<Integer> candidateResult = majorityElementCandidate(arr);
        int candidate = candidateResult.getResult().intValue();

        // Second, verify that candidate got at least n/2 occurrences - O(n)
        int counter = 0;
        for (int n : arr) {
            candidateResult.countStep();

            if (n == candidate) {
                counter++;
            }
        }

        // If counter >= n/2, return the candidate. Otherwise, result is undefined
        if (counter < (arr.length / 2)) {
            candidateResult.setResult(Integer.valueOf(UNDEFINED));
        }

        return candidateResult;
    }

    private ActionResult<Integer> majorityElementCandidate(int[] arr) {
        ActionResult<Integer> result = new ActionResult<>();
        int candidate = UNDEFINED;
        int counter = 0;

        // O(n) - It's time to du du du du du du du du duel!!
        for (int n : arr) {
            result.countStep();

            if (counter == 0) {
                candidate = n;
            }

            counter += candidate == n ? 1 : -1;
        }

        result.setResult(Integer.valueOf(candidate));
        return result;
    }

    private void printResult(int[] arr, ActionResult<Integer> majorityElement) {
        System.out.println("Array: " + Arrays.toString(arr) + " (size=" + arr.length + ")");
        if (majorityElement.getResult().intValue() == UNDEFINED) {
            System.out.print("No majority element in this array");
        } else {
            System.out.print("Majority element is: " + majorityElement);
        }

        System.out.println(".    (Steps count = " + majorityElement.getStepsCount() + ", which is 2n -> O(n))");
    }
}

