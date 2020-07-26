package algorithm;

import java.util.*;

/**
 * @author Haim Adrian
 * @since 01-Jul-20
 */
public class GreedyActivitySelector implements AlgorithmIfc {

    @Override
    public void execute() {
        int activityCount = readInt("Enter amount of activities:");
        System.out.println("There is a difference between activity interval parentheses. For example, [2,4], [4,6] cannot be chosen together, where" +
                           " (2,4), (4,6) can. That's why I need to know what parentheses to check.");
        String parentheses = readLine("Let me know how an activity interval looks like. For example: () or []");
        String leftParentheses = parentheses.substring(0, 1);
        String rightParentheses = parentheses.substring(1, 2);

        List<Interval> intervals = new ArrayList<>(activityCount);
        System.out.println("You will be asked to enter Si (Start-Time) and Fi (Finish-Time) for each activity. Enter the start time and finish time one by one");

        for (int i = 1; i <= activityCount; i++) {
            System.out.println("Enter activity #" + i);
            intervals.add(new Interval(i, readInt(""), readInt(""), leftParentheses, rightParentheses));
        }

        // Sort activities based on finish time
        intervals.sort(Comparator.comparingInt(interval -> interval.finish));

        // Nice format of all activities
        String activitiesString = prettyFormatOfActivities(intervals);

        System.out.println("Activities sorted based on finish time:");
        System.out.println(activitiesString);

        List<Interval> selectedActivities = new ArrayList<>();
        selectedActivities.add(intervals.get(0));
        for (int i = 1; i < intervals.size(); i++) {
            Interval lastActivity = selectedActivities.get(selectedActivities.size() - 1);
            Interval currActivity = intervals.get(i);

            if ((lastActivity.finish < currActivity.start) ||
                ((lastActivity.finish == currActivity.start) && (lastActivity.rightParentheses.equals(")") || currActivity.leftParentheses.equals("(")))) {
                selectedActivities.add(currActivity);
            }
        }

        activitiesString = prettyFormatOfActivities(selectedActivities);
        System.out.println("Selected activities:");
        System.out.println(activitiesString);
        System.out.println("Please note that there might be several selections! Look above at the activities order and verify it.");

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

    private static class Interval {
        int id;
        int start;
        int finish;
        String leftParentheses;
        String rightParentheses;

        public Interval(int id, int start, int finish, String leftParentheses, String rightParentheses) {
            this.id = id;
            this.start = start;
            this.finish = finish;
            this.leftParentheses = leftParentheses;
            this.rightParentheses = rightParentheses;
        }

        @Override
        public String toString() {
            return "V" + id + ": " + leftParentheses + start + ", " + finish + rightParentheses;
        }
    }
}

