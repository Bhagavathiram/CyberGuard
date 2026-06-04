import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CyberGuard extends JFrame {

    private JPasswordField passwordField;
    private JLabel resultLabel;
    private JTextArea suggestionArea;

    public CyberGuard() {

        setTitle("CyberGuard - Password Strength Analyzer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(
                "CyberGuard Password Analyzer",
                SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        passwordField = new JPasswordField(20);

        JButton checkButton = new JButton("Check Strength");
        JButton generateButton = new JButton("Generate Password");

        resultLabel = new JLabel(" ");

        suggestionArea = new JTextArea();
        suggestionArea.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Password:"));
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkButton);
        buttonPanel.add(generateButton);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4,1));

        topPanel.add(title);
        topPanel.add(inputPanel);
        topPanel.add(buttonPanel);
        topPanel.add(resultLabel);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(suggestionArea), BorderLayout.CENTER);

        checkButton.addActionListener(e -> analyzePassword());

        generateButton.addActionListener(e -> {
            passwordField.setText(generatePassword());
        });

        setVisible(true);
    }

    private void analyzePassword() {

        String password =
                new String(passwordField.getPassword());

        int score = 0;
        StringBuilder suggestions =
                new StringBuilder();

        if(password.length() >= 8)
            score++;
        else
            suggestions.append("Use at least 8 characters\n");

        if(password.matches(".*[A-Z].*"))
            score++;
        else
            suggestions.append("Add uppercase letters\n");

        if(password.matches(".*[a-z].*"))
            score++;
        else
            suggestions.append("Add lowercase letters\n");

        if(password.matches(".*\\d.*"))
            score++;
        else
            suggestions.append("Add numbers\n");

        if(password.matches(".*[^a-zA-Z0-9].*"))
            score++;
        else
            suggestions.append("Add special characters\n");

        if(score <= 2) {
            resultLabel.setText("Strength: WEAK (" + score + "/5)");
            resultLabel.setForeground(Color.RED);
        }
        else if(score <= 4) {
            resultLabel.setText("Strength: MEDIUM (" + score + "/5)");
            resultLabel.setForeground(Color.ORANGE);
        }
        else {
            resultLabel.setText("Strength: STRONG (" + score + "/5)");
            resultLabel.setForeground(Color.GREEN);
        }

        if(suggestions.length() == 0) {
            suggestions.append("Excellent Password!\n");
            suggestions.append("Meets all security requirements.");
        }

        suggestionArea.setText(suggestions.toString());
    }

    private String generatePassword() {

        String chars =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789!@#$%^&*";

        Random random = new Random();

        StringBuilder password =
                new StringBuilder();

        for(int i = 0; i < 12; i++) {

            password.append(
                    chars.charAt(
                            random.nextInt(chars.length())
                    )
            );
        }

        return password.toString();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                CyberGuard::new
        );
    }
}