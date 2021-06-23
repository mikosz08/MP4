package gui;

import models.player.Player;
import models.player.PlayerLocation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;

public class LoginGUI extends GuiUtilities {
    private JPanel mainLoginPanel;
    private JPanel northPanel;
    private JPanel southPanel;

    private JTextField classTextField;
    private JTextField usernameTextField;
    private JTextField locationNameTextField;
    private JTextField xTextField;
    private JTextField yTextField;
    private JTextField levelTextField;


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
        setTitle("Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private void initFields() {
        usernameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                usernameTextField.setText("");
            }
        });
    }

    private void initButtons(LoginGUI loginGUI) {

        Default.addActionListener(e -> {
            Random r = new Random();
            int userIndex = r.nextInt(Player.getPlayersExtent().size());
            usernameTextField.setText(Player.getPlayersExtent().get(userIndex).getNickname());
        });

        Submit.addActionListener(e -> {

            boolean validated = true;

            try {

                Player loggedPlayer = Player.getPlayersExtent()
                        .stream()
                        .filter(x -> x.getNickname().equals(usernameTextField.getText()))
                        .findAny().orElse(null);

                if (loggedPlayer == null) {
                    JOptionPane.showMessageDialog(this, "Can't find player!", "Try Again", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Login.setLoggedUser(loggedPlayer);

                System.out.printf("Logged as: %s\n", Login.getLoggedUser());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Wrong Format!", "Try Again", JOptionPane.WARNING_MESSAGE);
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
