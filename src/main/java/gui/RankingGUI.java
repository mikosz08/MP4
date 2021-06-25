package gui;

import gui.Controllers.MainController;
import gui.Controllers.RankingController;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RankingGUI extends JDialog {
    private JPanel contentPane;
    private JButton rankingByLevelButton;
    private JButton cancelButton;
    private JButton rankingByRepButton;
    private JTable rankingTable;

    RankingController rankingController;
    MainController mainController;

    public RankingGUI(MainGUI mainGUI) {
        rankingController = new RankingController();
        mainController = new MainController();

        initFrame();
        initButtons(mainGUI);

        mainController.loadMembers(rankingTable);

    }

    private void initButtons(MainGUI mainGUI) {
        rankingByLevelButton.addActionListener(e -> rankingController.showRankingByLevel(rankingTable));

        rankingByRepButton.addActionListener(e -> {
            rankingController.showRankingByReputation(rankingTable);
        });

        cancelButton.addActionListener(e -> rankingController.closeRankingDialog(this, mainGUI));

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
        setTitle("Global Ranking");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }


}
