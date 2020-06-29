package algorithm;

import util.ActionResult;

import java.util.*;

/**
 * @author Haim Adrian
 * @since 27-Jun-20
 */
public class KMP implements AlgorithmIfc{

    @Override
    public void execute() {
        // Arrange
        //String longPattern = "ababbabbababbababbabb";
        String longPattern = readLine("Enter pattern to compute prefix (PI) for: (for example: ababbabbababbababbabb)");
        boolean search = readYesNo("Would you like to see full search example in action? (y/n)");

        // Act
        ActionResult<int[]> piForLongPattern = computePrefix(longPattern);

        // Output
        System.out.println("Pattern: " + longPattern);
        System.out.println(Arrays.toString(longPattern.toCharArray()));
        System.out.println(Arrays.toString(piForLongPattern.getResult()));
        System.out.println(piForLongPattern.getStepsCount() + " steps took to compute prefix");

        if (search) {
            String text = getNoaKilaLyrics();
            String pattern = "\tBangin' on my drum\n\tBangin' on my drum";
            ActionResult<int[]> piForNoaKilaPattern = computePrefix(pattern);
            ActionResult<List<Integer>> patternOccurrences = kmpMatcher(text, pattern, piForNoaKilaPattern.getResult());
            System.out.println();
            System.out.println("Text is: " + System.lineSeparator() + text);
            System.out.println("Pattern: " + System.lineSeparator() + "\"" + pattern + "\"");
            System.out.println("PI: " + Arrays.toString(piForNoaKilaPattern.getResult()));
            System.out.println("|T| = " + text.length() + ",    |P| = " + pattern.length());
            System.out.println(piForNoaKilaPattern.getStepsCount() + " steps took to compute prefix");
            System.out.println(patternOccurrences.getStepsCount() + " steps took to find all occurrences of pattern in text - O(|T|+|P|)");
            System.out.println("Occurrences of pattern in text are at: " + patternOccurrences.getResult());
        }
    }

    private ActionResult<List<Integer>> kmpMatcher(CharSequence text, CharSequence pattern, int[] pi) {
        ActionResult<List<Integer>> result = new ActionResult<>();
        List<Integer> occurrences = new ArrayList<>(); // Keep all occurrences of pattern in text.

        int k = 0;
        for (int i = 0; i < text.length(); i++) {
            result.countStep();
            while ((k > 0) && (pattern.charAt(k) != text.charAt(i))) {
                result.countStep();
                k = pi[k-1];
            }

            if (pattern.charAt(k) == text.charAt(i)) {
                k++;
            }

            // Check if we have arrived to the end of pattern
            if (k == pattern.length()) {
                // Keep this occurrence (Get the beginning of the occurrence)
                occurrences.add(Integer.valueOf(i - k + 1));

                // Now continue to compare after the current sequence
                k = pi[k-1];
            }
        }

        result.setResult(occurrences);
        return result;
    }

    private ActionResult<int[]> computePrefix(CharSequence pattern) {
        ActionResult<int[]> result = new ActionResult<>();

        int[] pi = new int[pattern.length()];
        int i = 0, k = 0;
        pi[i] = 0;

        result.countStep();
        for (i = 1; i < pattern.length(); i++) {
            result.countStep();

            // Traverse back on PI array in case we encounter a difference and we are in a match
            while ((k > 0) && (pattern.charAt(k) != pattern.charAt(i))) {
                result.countStep();
                k = pi[k-1];
            }

            if (pattern.charAt(k) == pattern.charAt(i)) {
                k++;
            }

            pi[i] = k;
        }

        result.setResult(pi);
        return result;
    }

    public static String getNoaKilaLyrics() {
        //@formatter:off
        return "\tI know you got a skin tight alibi\n" +
               "\tThe rhythm made you do it so you did it right\n" +
               "\tYou said it's only once now it's every night, every night\n" +
               "\tSo how you think you're free but you're at my crib\n" +
               "\tAnd how you givin' up when you goin' in\n" +
               "\tYou say you gotta leave but you know that I\n" +
               "\tKnow that I\n" +
               "\tI can feel you getting closer\n" +
               "\tFighting like a soldier\n" +
               "\tBut we can't resist the battle cry\n" +
               "\tBangin' on my drum boy\n" +
               "\tLoud as the earth is shakin'\n" +
               "\tStrong like the bed we're breaking\n" +
               "\tShow me what you do\n" +
               "\tWhen it's me and you\n" +
               "\tLay it on my body let the\n" +
               "\tBeat get you rowdy baby\n" +
               "\tHit me like you do\n" +
               "\tWanna feel you\n" +
               "\tBangin' on my drum\n" +
               "\tBangin' on my drum\n" +
               "\tBangin' on my\n" +
               "\tKeep me on the dark side of the moon\n" +
               "\tSwing low, high tide that's the push and pull\n" +
               "\t'Cause you know it's a win even when you lose\n" +
               "\t'Cause even when it's off we're still on a groove\n" +
               "\tAnd I can't get enough of the way you move\n" +
               "\tI don't know 'bout later but I'll be ready soon\n" +
               "\tReady soon\n" +
               "\tI can feel you getting closer\n" +
               "\tFighting like a soldier\n" +
               "\tBut we can't resist the battle cry\n" +
               "\tBangin' on my drum boy\n" +
               "\tLoud as the earth is shakin'\n" +
               "\tStrong like the bed we're breaking\n" +
               "\tShow me what you do\n" +
               "\tWhen it's me and you\n" +
               "\tLay it on my body let the\n" +
               "\tBeat get you rowdy baby\n" +
               "\tHit me like you do\n" +
               "\tWanna feel you\n" +
               "\tBangin' on my drum\n" +
               "\tBangin' on my drum\n" +
               "\tLoud as the earth is shakin'\n" +
               "\tStrong like the bed we're breaking\n" +
               "\tShow me what you do\n" +
               "\tWhen it's me and you\n" +
               "\tLay it on my body let the\n" +
               "\tBeat get you rowdy baby\n" +
               "\tHit me like you do\n" +
               "\tWanna feel you\n" +
               "\tBangin' on my drum\n" +
               "\tBangin' on my drum boy\n" +
               "\tBangin' on my drum\n" +
               "\tBangin' on my drum";
        //@formatter:on
    }
}

