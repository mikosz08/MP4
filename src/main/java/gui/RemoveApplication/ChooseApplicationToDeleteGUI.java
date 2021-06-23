package gui.RemoveApplication;

import gui.Controllers.MainController;
import gui.Controllers.RemoveApplicationController;
import gui.MainGUI;
import models.guild.Guild;

import javax.swing.*;
import java.awt.event.*;

public class ChooseApplicationToDeleteGUI extends JDialog {
    private JPanel contentPane;
    private JButton removeApplication;
    private JButton buttonCancel;
    private JTable playerApplicationsTable;

    MainController mainController;
    RemoveApplicationController removeApplicationController;

    public ChooseApplicationToDeleteGUI(Guild selectedGuild, MainGUI mainGUI) {
        mainController = new MainController();
        removeApplicationController = new RemoveApplicationController();
        mainGUI.setEnabled(false);
        initFrame();
        initButtons(mainGUI, selectedGuild);

        mainController.loadApplications(playerApplicationsTable, selectedGuild);

    }

    private void initFrame() {
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setTitle("Choose Application");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private void initButtons(MainGUI mainGUI, Guild selectedGuild) {

        removeApplication.addActionListener(e -> {
            removeApplicationController.deleteChoosedApplication(this, mainGUI, playerApplicationsTable.getSelectedRow());
        });

        buttonCancel.addActionListener(e -> {
            removeApplicationController.closeRemoveApplicationGUI(this, mainGUI);
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainGUI.setEnabled(true);
                dispose();
            }
        });

    }

}
