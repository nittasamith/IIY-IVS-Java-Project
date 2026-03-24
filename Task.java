import java.time.LocalDate;

public class Task {

    private int taskId;
    private String taskName;
    private int priority;
    private LocalDate deadline;
    private String status;
    private long timeSpent;

    public Task(int taskId, String taskName, int priority, LocalDate deadline) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.priority = priority;
        this.deadline = deadline;
        this.status = "Pending";
        this.timeSpent = 0;
    }

    public int getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public int getPriority() { return priority; }
    public LocalDate getDeadline() { return deadline; }
    public String getStatus() { return status; }
    public long getTimeSpent() { return timeSpent; }

    public void setStatus(String status) { this.status = status; }
    public void addTimeSpent(long time) { this.timeSpent += time; }

    @Override
    public String toString() {
        return taskId + "," + taskName + "," + priority + "," +
               deadline + "," + status + "," + timeSpent;
    }

    public static Task fromString(String line) {
        String[] parts = line.split(",");
        Task t = new Task(
                Integer.parseInt(parts[0]),
                parts[1],
                Integer.parseInt(parts[2]),
                LocalDate.parse(parts[3])
        );
        t.status = parts[4];
        t.timeSpent = Long.parseLong(parts[5]);
        return t;
    }
}