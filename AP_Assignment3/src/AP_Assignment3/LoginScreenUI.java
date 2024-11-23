package AP_Assignment3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreenUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final String FILE_NAME = "CustomerHistory.ser";
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private Customer loginMember;
    private boolean isAdmin = false;

    public LoginScreenUI() {
        // Setup the frame
        setTitle("Login Screen");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        // Add components
        addComponents();

        // Show the frame
        setVisible(true);
    }

    private void addComponents() {
        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        // Message label
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginActionListener());

        // Add to the frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(messageLabel);
        add(loginButton);
    }

    // Action listener for login
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            synchronized (LoginScreenUI.this) {
                try {
                        // Customer login
                        CustomerHistory memberList = CustomerHistory.loadFromFile(FILE_NAME);
                        loginMember = memberList.findCustomerByUsername(username);

                        if (memberList.validateCustomer(username, password)) {
                        	if (username.equals("admin")) {isAdmin = true;}
                            LoginScreenUI.this.notifyAll(); // Notify waiting threads
                            dispose();
                        } else {
                            throw new InvalidLoginException("Login failed. Username or password does not match.");
                        }
                    
                } catch (InvalidLoginException ex) {
                    messageLabel.setText(ex.getMessage());
                } catch (Exception ex) {
                    messageLabel.setText("Error loading customer data.");
                    ex.printStackTrace();
                }
            }
        }
    }

    // This method waits until either loginMember or isAdmin is set
    public synchronized Customer getLogin() {
        while (loginMember == null && !isAdmin) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return isAdmin ? null : loginMember;
    }

    public synchronized boolean getAdmin() {
        return isAdmin;
    }
}
