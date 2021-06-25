package gui;

import gui.Controllers.KickMemberController;
import gui.Controllers.MainController;

import javax.swing.*;
import java.awt.event.*;

public class KickMemberGUI extends JDialog {
    private JPanel contentPane;
    private JButton kickButton;
    private JButton cancelButton;
    private JTable membersTable;

    KickMemberController kickMemberController;
    MainController mainController;

    public KickMemberGUI(MainGUI mainGUI, JTable mainTable) {
        kickMemberController = new KickMemberController();
        mainController = new MainController();

        initFrame();
        initButton(mainGUI, mainTable);

        mainController.loadMembers(membersTable);
    }

    private void initButton(MainGUI mainGUI, JTable mainTable) {
        kickButton.addActionListener(e -> {
            kickMemberController.kickPlayer(this, mainGUI, membersTable.getSelectedRow(), mainTable);
        });

        cancelButton.addActionListener(e -> {
            System.out.println();
            kickMemberController.closeKickMemberDialog(this, mainGUI);
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
        setTitle("Kick Member");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

}
