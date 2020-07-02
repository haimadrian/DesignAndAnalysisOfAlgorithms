package algorithm;

import java.util.*;

/**
 * @author Haim Adrian
 * @since 01-Jul-20
 */
public class ComputeOpt implements AlgorithmIfc {

    @Override
    public void execute() {
        int activityCount = readInt("Enter amount of activities:");
        System.out.println("There is a difference between activity interval parentheses. For example, [2,4], [4,6] cannot be chosen together, where" +
                           " (2,4), (4,6) can. That's why I need to know what parentheses to check.");
        String parentheses = readLine("Let me know how an activity interval looks like. For example: () or []");
        String leftParentheses = parentheses.substring(0, 1);
        String rightParentheses = parentheses.substring(1, 2);

        List<Interval> intervals = new ArrayList<>(activityCount);
        System.out.println("You will be asked to enter Si (Start-Time) and Fi (Finish-Time) and then weight for each activity. Enter each number separately");

        for (int i = 1; i <= activityCount; i++) {
            System.out.println("Enter activity #" + i);
            intervals.add(new Interval(i, readInt(""), readInt(""), readInt(""), leftParentheses, rightParentheses));
        }

        // Sort activities based on finish time
        intervals.sort(Comparator.comparingInt(interval -> interval.finish));

        int[] p = buildPArray(intervals);

        // Now compute optimum value
        int[] m = new int[intervals.size() + 1];
        m[0] = 0;
        for (int i = 0; i < intervals.size(); i++) {
            Interval currActivity = intervals.get(i);
            m[i+1] = Math.max(currActivity.weight + m[p[i]], m[i]);
        }

        // Nice format of all activities
        String activitiesString = prettyFormatOfActivities(intervals);

        System.out.println("Activities sorted based on finish time:");
        System.out.println(activitiesString);

        System.out.println("P array (for each activity, which activity is the nearest from left that does not intersect with it):");
        System.out.println(Arrays.toString(p));
        System.out.println("M array (the optimum computation, based on: M[i]=max(Vi + M[p[i]], M[i-1]). Note that M[0]=0");
        System.out.println(Arrays.toString(m));
        System.out.println("The optimum selection is:");
        findSolution(intervals, m, p, intervals.size() - 1);
    }

    private String prettyFormatOfActivities(List<Interval> intervals) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intervals.size(); i++) {
            Interval currActivity = intervals.get(i);
            for (int j = 0; j < currActivity.start; j++) {
                sb.append(" ");
            }
            for (int j = currActivity.start; j < currActivity.finish; j++) {
                sb.append("-");
            }
            sb.append(" ").append(currActivity.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    private void findSolution(List<Interval> intervals, int[] m, int[] p, int i) {
        if (i == 0) {
            return;
        }

        Interval currInterval = intervals.get(i);
        if (currInterval.weight + m[p[i]] >= m[i-1]) {
            System.out.println(currInterval);
            findSolution(intervals, m, p, p[i]);
        } else {
            findSolution(intervals, m, p, i-1);
        }
    }

    private int[] buildPArray(List<Interval> intervals) {
        int[] p = new int[intervals.size()];
        p[0] = 0;

        for (int i = intervals.size() - 1; i > 0; i--) {
            Interval currActivity = intervals.get(i);

            // Find the nearest activity from left that has no intersection with current activity.
            boolean found = false;
            for (int j = i - 1; j >= 0 && !found; j--) {
                Interval lastActivity = intervals.get(j);
                if ((lastActivity.finish < currActivity.start) ||
                    ((lastActivity.finish == currActivity.start) && (lastActivity.rightParentheses.equals(")") || currActivity.leftParentheses.equals("(")))) {
                    found = true;
                    p[i] = j + 1;
                }
            }
            if (!found) {
                p[i] = 0;
            }
        }

        return p;
    }

    private static class Interval {
        int id;
        int start;
        int finish;
        int weight;
        String leftParentheses;
        String rightParentheses;

        public Interval(int id, int start, int finish, int weight, String leftParentheses, String rightParentheses) {
            this.id = id;
            this.start = start;
            this.finish = finish;
            this.weight = weight;
            this.leftParentheses = leftParentheses;
            this.rightParentheses = rightParentheses;
        }

        @Override
        public String toString() {
            return "V" + id + ": " + leftParentheses + start + ", " + finish + rightParentheses + ", weight=" + weight;
        }
    }
}

