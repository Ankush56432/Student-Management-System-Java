import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;

    public LoginScreen() {
        setTitle("Login - Student Management System");
        setSize(350, 260); // increased height to fit 3 buttons
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Username and password fields
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Show password checkbox
        showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(e -> togglePassword());
        add(showPassword);
        add(new JLabel()); // placeholder

        // Buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton forgotButton = new JButton("Forgot Password?");
        add(loginButton);
        add(registerButton);
        add(forgotButton); // new button

        // Action Listeners
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterScreen().setVisible(true);
        });
        forgotButton.addActionListener(e -> forgotPassword());
    }

    private void togglePassword() {
        passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '•');
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (isValidLogin(username, password)) {
            dispose();
            new StudentManagementGUI().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private boolean isValidLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading users.txt");
        }
        return false;
    }

    // ✅ Forgot password method
    private void forgotPassword() {
        String username = JOptionPane.showInputDialog(this, "Enter your username:");
        if (username == null || username.trim().isEmpty()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    JOptionPane.showMessageDialog(this, "Your password is: " + parts[1]);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Username not found.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading users.txt");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginScreen().setVisible(true);
        });
    }
}
