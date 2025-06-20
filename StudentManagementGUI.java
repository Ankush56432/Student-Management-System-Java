import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentManagementGUI extends JFrame {
    private ArrayList<Student> studentList = new ArrayList<>();
    private int nextId = 1;

    private JTextField nameField, courseField, emailField, idField;
    private JTextArea displayArea;

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Course:"));
        courseField = new JTextField();
        inputPanel.add(courseField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("ID (for Update/Delete):"));
        idField = new JTextField();
        inputPanel.add(idField);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(exitBtn);
        add(buttonPanel, BorderLayout.CENTER);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        // Button Actions
        addBtn.addActionListener(e -> addStudent());
        viewBtn.addActionListener(e -> viewStudents());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void addStudent() {
        String name = nameField.getText();
        String course = courseField.getText();
        String email = emailField.getText();
        if (name.isEmpty() || course.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        Student s = new Student(nextId++, name, course, email);
        studentList.add(s);
        clearFields();
        JOptionPane.showMessageDialog(this, "Student Added.");
    }

    private void viewStudents() {
        if (studentList.isEmpty()) {
            displayArea.setText("No students to display.");
            return;
        }

        StringBuilder sb = new StringBuilder("ID | Name | Course | Email\n");
        for (Student s : studentList) {
            sb.append(s.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            for (Student s : studentList) {
                if (s.getId() == id) {
                    s.setName(nameField.getText());
                    s.setCourse(courseField.getText());
                    s.setEmail(emailField.getText());
                    JOptionPane.showMessageDialog(this, "Student Updated.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Student ID not found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid numeric ID.");
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            boolean removed = studentList.removeIf(s -> s.getId() == id);
            if (removed) {
                JOptionPane.showMessageDialog(this, "Student Deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "Student ID not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid numeric ID.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        courseField.setText("");
        emailField.setText("");
        idField.setText("");
    }

    // No main method here anymore. The system will launch this class after login.
}
