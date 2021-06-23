package gui;

import gui.Controllers.CreateGuildController;
import gui.Controllers.MainController;
import models.guild.Faction;
import models.guild.Guild;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateGuildGUI extends JDialog {
    private JPanel contentPane;
    private JButton createButton;
    private JButton cancelButton;
    private JTextField guildNameInfoTextField;
    private JTextField factionInfoTextField;
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
        contentPane.addComponentListener(new ComponentAdapter() {
        });
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
                mainGUI.setEnabled(true);
                Guild info = createGuildController.createGuild(guildNameTextField.getText(), (String) factionComboBox.getSelectedItem(), this);
                mainController.showGuildInfo(info, guildInfoTextArea);
                mainController.loadGuilds(mainTable);
                //ExtentManager.save();
            }
        });

        cancelButton.addActionListener(e -> {
            mainGUI.setEnabled(true);
            createGuildController.onCancel(this);
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainGUI.setEnabled(true);
            }
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
