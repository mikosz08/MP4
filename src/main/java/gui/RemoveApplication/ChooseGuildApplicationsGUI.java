package gui.RemoveApplication;

import gui.Controllers.MainController;
import gui.Controllers.RemoveApplicationController;
import gui.MainGUI;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChooseGuildApplicationsGUI extends JDialog {
    private JPanel contentPane;
    private JButton chooseButton;
    private JButton cancelButton;
    private JTable guildTable;
    MainController mainController;
    RemoveApplicationController removeApplicationController;

    public ChooseGuildApplicationsGUI(MainGUI mainGUI) {
        mainController = new MainController();
        removeApplicationController = new RemoveApplicationController();

        initFrame();
        initButtons(mainGUI);
        mainController.loadGuilds(guildTable);

    }

    private void initFrame() {
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setTitle("Choose Guild");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private void initButtons(MainGUI mainGUI) {
        chooseButton.addActionListener(e -> {
            removeApplicationController.chooseGuild(this, mainGUI, guildTable.getSelectedRow());
        });

        cancelButton.addActionListener(e -> {
            removeApplicationController.closeChooseGuildGUI(this, mainGUI);
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainGUI.setEnabled(true);
                dispose();
            }
        });

    }

}
