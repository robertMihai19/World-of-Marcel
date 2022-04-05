package WoM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginFrame implements ActionListener {

    JFrame frame = new JFrame();
    Container container = frame.getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");
    ArrayList<Account> accounts;

    LoginFrame(ArrayList<Account> accounts) {
        //Calling methods inside constructor.
        this.accounts = accounts;
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        container.setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
    }

    public void run() {
        frame.setTitle("World of Marcel");
        frame.setVisible(true);
        frame.setBounds(10, 10, 570, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setLocationAndSize() {
        //Setting location and Size of each components using setBounds() method.
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        resetButton.setBounds(200, 300, 100, 30);


    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = userTextField.getText();
            String parola = passwordField.getText();
//            email = "genna1951@hotmail.red";
//            parola = "123456";
            passwordField.setText("");
            userTextField.setText("");
            for (Account cont : accounts) {
                Credentials validate = cont.getInfo().getCredentials();
                if (validate.getEmail().equals(email) && validate.getPassword().equals(parola)) {
                    ChooseCharacter ceva = new ChooseCharacter(cont);
                    JFrame selectCharacter = ceva.run();
                    frame.setVisible(false);
                    selectCharacter.setVisible(true);
                }
            }

        } else if (e.getSource() == resetButton) {
            passwordField.setText("");
            userTextField.setText("");
        }
    }
}
