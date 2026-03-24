import java.time.LocalDate;
import java.util.List;

public class Report {

    public static String generateReport(List<Task> tasks) {

        int total = tasks.size();
        int completed = 0;
        long totalTime = 0;

        for (Task t : tasks) {
            if (t.getStatus().equalsIgnoreCase("Completed"))
                completed++;
            totalTime += t.getTimeSpent();
        }

        double score = total == 0 ? 0 :
                ((double) completed / total) * 100;

        return "------ Productivity Report ------\n\n" +
                "Total Tasks: " + total + "\n" +
                "Completed Tasks: " + completed + "\n" +
                "Total Time Spent (ms): " + totalTime + "\n" +
                "Productivity Score: " + score + "%\n" +
                "Report Date: " + LocalDate.now();
    }
}