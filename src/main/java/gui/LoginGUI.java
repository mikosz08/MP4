package gui;

import models.player.Player;
import models.player.PlayerLocation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginGUI extends GuiUtilities {
    private JPanel mainLoginPanel;
    private JPanel buttonPanel;

    private JTextField enterYourDataTextField;
    private JTextField usernameTextField;
    private JTextField levelTextField;
    private JTextField locationNameTextField;
    private JTextField xTextField;
    private JTextField yTextField;
    private JTextField classTextField;

    private JButton Default;
    private JButton Submit;

    public LoginGUI() {
        initFrame();
        initFields();
        initButtons(this);
    }

    private void initFrame() {
        setContentPane(mainLoginPanel);
        pack();
        setLocationRelativeTo(null);
        setTitle("Companion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private void initFields() {

        levelTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                levelTextField.setText("");
            }
        });

        classTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                classTextField.setText("");
            }
        });

        usernameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                usernameTextField.setText("");
            }
        });

        locationNameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                locationNameTextField.setText("");
            }
        });

        xTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                xTextField.setText("");
            }
        });

        yTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                yTextField.setText("");
            }
        });

    }

    private void initButtons(LoginGUI loginGUI) {

        Default.addActionListener(e -> {
            usernameTextField.setText("xXMrPlayerXx");
            levelTextField.setText("17");
            locationNameTextField.setText("Ragefire Chasm");
            xTextField.setText("19");
            yTextField.setText("-45");
            classTextField.setText("Student");
        });

        Submit.addActionListener(e -> {

            boolean validated = true;

            try {
                Login user = new Login(new Player(
                        usernameTextField.getText(),
                        Integer.parseInt(levelTextField.getText()),
                        new PlayerLocation(
                                locationNameTextField.getText(),
                                Integer.parseInt(xTextField.getText()),
                                Integer.parseInt(yTextField.getText())
                        ),
                        classTextField.getText()
                ));
                System.out.printf("Logged as: %s", user.getLoggedUser());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Wrong Type", "Warning", JOptionPane.WARNING_MESSAGE);
                validated = false;
            }

            if (validated) {
                openMainGUI();
            }

        });
    }

    private void openMainGUI() {
        new MainGUI();
        this.setEnabled(false);
        this.dispose();
    }

}
