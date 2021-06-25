package gui.applyToGuildGUI;

import gui.Controllers.ApplyToGuildController;
import gui.MainGUI;
import models.guild.Guild;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ApplicationFormGUI extends JDialog {
    private JPanel contentPane;
    private JButton sendButton;
    private JButton cancelButton;
    private JTextArea referalMessageJArea;
    private JRadioButton addMessageRadioButton;
    private JLabel choosedGuildInfoJLabel;

    ApplyToGuildController applyToGuildController;

    public ApplicationFormGUI(MainGUI mainGUI, Guild choosedGuild) {
        applyToGuildController = new ApplyToGuildController();

        mainGUI.setEnabled(false);

        choosedGuildInfoJLabel.setText("You want to apply to:" + choosedGuild.getGuildName() + '?');

        initFrame();
        initButtons(mainGUI, choosedGuild);
    }

    private void initButtons(MainGUI mainGUI, Guild choosedGuild) {

        sendButton.addActionListener(e -> {

            applyToGuildController.sendApplication(this, mainGUI, choosedGuild, referalMessageJArea.getText());
        });

        cancelButton.addActionListener(e -> {
            mainGUI.setEnabled(true);
            applyToGuildController.closeApplicationFormDialog(this, mainGUI);
        });

        addMessageRadioButton.addActionListener(e -> {
            referalMessageJArea.setEnabled(addMessageRadioButton.isEnabled());
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainGUI.setEnabled(true);
                dispose();
            }
        });

    }

    private void initFrame() {
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setTitle("Create Application Form");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

}
