package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainGUI extends JFrame {

    private JPanel mainPanel;
    private JPanel tablesPanel;

    private JPanel northTablePanel;
    private JButton guildsButton;
    private JButton membersButton;
    private JButton eventsButton;
    private JButton achievementsButton;
    private JButton shopButton;

    private JPanel centerTablePanel;
    private JTable mainTable;

    private JPanel menuPanel;

    private JPanel southMenuPanel;
    private JButton createGuildButton;
    private JButton applyToGuildButton;
    private JButton removeApplicationButton;
    private JButton editDayMessageButton;
    private JButton viewRankingButton;
    private JButton createEventButton;
    private JButton addApplicantButton;
    private JButton kickMemberButton;
    private JButton editDaySentenceButton;
    private JButton deleteGuildButton;

    private JPanel northMenuPanel;
    private JTextArea guildInfoTextArea;

    private JPanel southTablePanel;
    private JTextArea logTextArea;
    private JTextField loggedAS;

    public MainGUI() {
        initFrame();

        //field setup


    }

    private void initFrame() {
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setTitle("Companion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        loadTestTableData();
    }

    private void loadTestTableData() {

        String[] columnNames = {"Nick", "Level", "Location", "Day Message"};
        mainTable.setModel(new DefaultTableModel(null, columnNames));

    }


}
