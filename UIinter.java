import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UIinter {

    private TaskManager manager = new TaskManager();
    private TimeTracker tracker = new TimeTracker();

    public UIinter() {
        showLoginWindow();   // Start with Login (Professional touch)
    }

    // ---------------- LOGIN WINDOW ----------------
    private void showLoginWindow() {

        JFrame frame = new JFrame("Login - Smart Task Scheduler");
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(new EmptyBorder(20,30,20,30));

        JLabel title = new JLabel("SMART TASK SCHEDULER", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(0,70,140));

        JPanel form = new JPanel(new GridLayout(3,2,10,10));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        form.add(new JLabel("Username:"));
        form.add(userField);
        form.add(new JLabel("Password:"));
        form.add(passField);

        JButton loginBtn = new JButton("Login");
        form.add(new JLabel());
        form.add(loginBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);

        loginBtn.addActionListener(e -> {
            frame.dispose();
            showMainWindow();
        });
    }

    // ---------------- MAIN DASHBOARD ----------------
    private void showMainWindow() {

        JFrame frame = new JFrame("Smart Desktop Task Scheduler & Productivity Analyzer");
        frame.setSize(900,550);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header
        JLabel header = new JLabel("SMART DESKTOP TASK SCHEDULER & PRODUCTIVITY ANALYZER", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(new Color(0,70,140));
        header.setBorder(new EmptyBorder(15,10,15,10));

        mainPanel.add(header, BorderLayout.NORTH);

        // Center Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3,3,20,20));
        buttonPanel.setBorder(new EmptyBorder(40,80,40,80));

        JButton add = createButton("Add Task");
        JButton update = createButton("Update Task");
        JButton delete = createButton("Delete Task");
        JButton search = createButton("Search Task");
        JButton view = createButton("View Tasks");
        JButton track = createButton("Time Tracking");
        JButton report = createButton("Productivity Report");

        buttonPanel.add(add);
        buttonPanel.add(update);
        buttonPanel.add(delete);
        buttonPanel.add(search);
        buttonPanel.add(view);
        buttonPanel.add(track);
        buttonPanel.add(report);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Actions (No Logic Changed)
        add.addActionListener(e -> showAddTaskWindow());
        update.addActionListener(e -> showUpdateTaskWindow());
        search.addActionListener(e -> showSearchTaskWindow());
        view.addActionListener(e -> showViewTaskWindow());
        delete.addActionListener(e -> showDeleteTaskWindow());
        track.addActionListener(e -> showTimeTrackingWindow());
        report.addActionListener(e -> showReportWindow());
    }

    // Reusable Professional Button
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(230,240,255));
        btn.setBorder(new LineBorder(new Color(0,70,140),2,true));
        return btn;
    }

    // ---------------- ADD TASK ----------------
    private void showAddTaskWindow() {

        JFrame frame = createFormFrame("Add New Task");

        JPanel panel = createFormPanel();

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priorityField = new JTextField();
        JTextField deadlineField = new JTextField();

        panel.add(new JLabel("Task ID:"));
        panel.add(idField);
        panel.add(new JLabel("Task Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Priority:"));
        panel.add(priorityField);
        panel.add(new JLabel("Deadline (yyyy-mm-dd):"));
        panel.add(deadlineField);

        JButton saveBtn = createButton("Save Task");
        panel.add(new JLabel());
        panel.add(saveBtn);

        frame.add(panel);
        frame.setVisible(true);

        saveBtn.addActionListener(e -> {
            try {
                manager.addTask(
                        Integer.parseInt(idField.getText()),
                        nameField.getText(),
                        Integer.parseInt(priorityField.getText()),
                        deadlineField.getText()
                );
                JOptionPane.showMessageDialog(frame,"Task Added Successfully");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,"Invalid Input");
            }
        });
    }

    // ---------------- VIEW TASKS ----------------
    private void showViewTaskWindow() {

        JFrame frame = new JFrame("View Tasks");
        frame.setSize(750,400);
        frame.setLocationRelativeTo(null);

        String[] columns = {"ID","Name","Priority","Deadline","Status","Time Spent(ms)"};
        DefaultTableModel model = new DefaultTableModel(columns,0);

        List<Task> tasks = manager.getAllTasks();

        for(Task t : tasks) {
            model.addRow(new Object[]{
                    t.getTaskId(),
                    t.getTaskName(),
                    t.getPriority(),
                    t.getDeadline(),
                    t.getStatus(),
                    t.getTimeSpent()
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        frame.add(new JScrollPane(table));
        frame.setVisible(true);
    }

    // ---------------- OTHER WINDOWS (UI SAME LOGIC) ----------------
    private void showDeleteTaskWindow() {
        String idStr = JOptionPane.showInputDialog("Enter Task ID to Delete:");
        manager.deleteTask(Integer.parseInt(idStr));
        JOptionPane.showMessageDialog(null,"Task Deleted Successfully");
    }

    private void showUpdateTaskWindow() {
        String idStr = JOptionPane.showInputDialog("Enter Task ID:");
        String status = JOptionPane.showInputDialog("New Status:");
        manager.updateStatus(Integer.parseInt(idStr), status);
        JOptionPane.showMessageDialog(null,"Task Updated");
    }

    private void showSearchTaskWindow() {
        String idStr = JOptionPane.showInputDialog("Enter Task ID:");
        Task t = manager.searchTask(Integer.parseInt(idStr));

        if(t != null)
            JOptionPane.showMessageDialog(null,
                    "ID: " + t.getTaskId() +
                    "\nName: " + t.getTaskName() +
                    "\nPriority: " + t.getPriority() +
                    "\nDeadline: " + t.getDeadline() +
                    "\nStatus: " + t.getStatus());
        else
            JOptionPane.showMessageDialog(null,"Task Not Found");
    }

    private void showTimeTrackingWindow() {
        JFrame frame = createFormFrame("Time Tracking");
        JPanel panel = createFormPanel();

        JTextField idField = new JTextField();
        JLabel statusLabel = new JLabel("Not Tracking");

        JButton startBtn = createButton("Start");
        JButton stopBtn = createButton("Stop");

        panel.add(new JLabel("Task ID:"));
        panel.add(idField);
        panel.add(new JLabel("Status:"));
        panel.add(statusLabel);
        panel.add(startBtn);
        panel.add(stopBtn);

        frame.add(panel);
        frame.setVisible(true);

        startBtn.addActionListener(e -> {
            tracker.startTracking();
            statusLabel.setText("Tracking...");
        });

        stopBtn.addActionListener(e -> {
            long time = tracker.stopTracking();

            try {
                int id = Integer.parseInt(idField.getText());
                Task t = manager.searchTask(id);

                if(t != null) {
                    t.addTimeSpent(time);
                    manager.saveTasks();
                    statusLabel.setText("Stopped");
                    JOptionPane.showMessageDialog(frame,
                            "Time Recorded: " + (time/1000) + " seconds");
                } else {
                    JOptionPane.showMessageDialog(frame,"Task Not Found");
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(frame,"Invalid Task ID");
            }
        });
    }

    private void showReportWindow() {

        String report = Report.generateReport(manager.getAllTasks());

        JTextArea area = new JTextArea(report);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setEditable(false);

        JFrame frame = new JFrame("Productivity Report");
        frame.setSize(600,400);
        frame.add(new JScrollPane(area));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // --------- UI HELPER METHODS ---------
    private JFrame createFormFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setSize(450,350);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(6,2,10,10));
        panel.setBorder(new EmptyBorder(20,30,20,30));
        return panel;
    }
}