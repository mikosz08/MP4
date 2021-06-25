package gui;

import models.player.Player;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class LoginGUI extends JFrame {

    private JPanel mainLoginPanel;
    private JTextField usernameTextField;
    private JPanel southPanel;
    private JPanel northPanel;
    private JButton Default;
    private JButton Submit;

    public LoginGUI() {
        initFrame();
        initFields();
        initButtons();
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

    private void initButtons() {
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
