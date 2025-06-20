import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RegisterScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;

    public RegisterScreen() {
        setTitle("Register - Student Management System");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("New Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("New Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(e -> togglePassword());
        add(showPassword);
        add(new JLabel());

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");
        add(registerBtn);
        add(backBtn);

        registerBtn.addActionListener(e -> registerUser());
        backBtn.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });
    }

    private void togglePassword() {
        passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '•');
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        if (userExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists.");
            return;
        }

        try (FileWriter fw = new FileWriter("users.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(username + "," + password);
            bw.newLine();
            JOptionPane.showMessageDialog(this, "User registered successfully!");
            dispose();
            new LoginScreen().setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to users.txt");
        }
    }

    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File might not exist yet – that's okay
        }
        return false;
    }
}
