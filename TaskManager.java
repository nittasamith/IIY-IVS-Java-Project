import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class TaskManager {

    private List<Task> tasks = new ArrayList<>();
    private final String FILE_NAME = "tasks.txt";

    public TaskManager() {
        loadTasks();
    }

    public void addTask(int id, String name, int priority, String deadlineStr) {
        LocalDate deadline = LocalDate.parse(deadlineStr);
        Task task = new Task(id, name, priority, deadline);
        tasks.add(task);
        saveTasks();
    }

    public void deleteTask(int id) {
        tasks.removeIf(t -> t.getTaskId() == id);
        saveTasks();
    }

    public Task searchTask(int id) {
        for (Task t : tasks) {
            if (t.getTaskId() == id) return t;
        }
        return null;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void updateStatus(int id, String status) {
        Task t = searchTask(id);
        if (t != null) {
            t.setStatus(status);
            saveTasks();
        }
    }

    public void saveTasks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task t : tasks) {
                pw.println(t.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(Task.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}