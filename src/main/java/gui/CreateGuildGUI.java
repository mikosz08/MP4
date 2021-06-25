package gui;

import gui.Controllers.CreateGuildController;
import gui.Controllers.MainController;
import models.guild.Faction;
import models.guild.Guild;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateGuildGUI extends JDialog {
    private JPanel contentPane;
    private JButton createButton;
    private JButton cancelButton;
    private JTextField guildNameTextField;
    private JComboBox<String> factionComboBox;

    CreateGuildController createGuildController;
    MainController mainController;

    public CreateGuildGUI(MainGUI mainGUI, JTextArea guildInfoTextArea, JTable mainTable) {
        createGuildController = new CreateGuildController();
        mainController = new MainController();

        initFrame();
        initComponents();
        initButtons(mainGUI, guildInfoTextArea, mainTable);
    }

    private void initFrame() {
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setTitle("Create Guild");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private void initButtons(MainGUI mainGUI, JTextArea guildInfoTextArea, JTable mainTable) {

        createButton.addActionListener(e -> {
            if (guildNameTextField.getText() == null || guildNameTextField.getText().trim().isBlank()) {
                guildNameTextField.setBackground(Color.RED);
            } else {

                Guild info = createGuildController.createGuild(guildNameTextField.getText(), (String) factionComboBox.getSelectedItem(), this, mainGUI, mainTable, guildInfoTextArea);
                mainController.showGuildInfo(info, guildInfoTextArea);
                mainController.loadGuilds(mainTable);
            }
        });

        cancelButton.addActionListener(e -> {
            createGuildController.onCancel(this, mainGUI);
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainGUI.setEnabled(true);
                dispose();
            }
        });

    }

    private void initComponents() {
        for (Faction f : Faction.getFactionsExtent()) {
            factionComboBox.addItem(f.getFactionName());
        }
    }

}
