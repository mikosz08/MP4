package gui.applyToGuildGUI;

import gui.Controllers.ApplyToGuildController;
import gui.Controllers.MainController;
import gui.MainGUI;
import models.guild.Guild;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChooseGuildToApplyGUI extends JDialog {
    private JPanel contentPane;
    private JButton chooseButton;
    private JButton cancelButton;
    private JTable guildsTable;

    MainController mainController;
    ApplyToGuildController applyToGuildController;

    public ChooseGuildToApplyGUI(MainGUI mainGUI) {
        mainController = new MainController();
        applyToGuildController = new ApplyToGuildController();

        initFrame();
        initButtons(mainGUI);

        mainController.loadGuilds(guildsTable);

    }

    private void initButtons(MainGUI mainGUI) {
        //main guid zeby na koniec zrobic setEnable true!!!!!!!!!!!
        chooseButton.addActionListener(e -> {
            if (guildsTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Choose a guild!", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Guild choosedGuild = Guild.getGuildsExtent().get(guildsTable.getSelectedRow());

            applyToGuildController.chooseGuild(this, mainGUI, choosedGuild);
        });

        cancelButton.addActionListener(e -> applyToGuildController.closeChooseGuildGUI(this, mainGUI));

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
        setTitle("Choose Guild");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }


}
