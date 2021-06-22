package gui;

import models.guild.Guild;
import models.player.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static java.util.Optional.*;


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
    private JTextField loggedAsTextField;

    DefaultTableModel tableModel;

    public MainGUI() {

        initFrame();

        initComponents();

        initButtons();

        loadGuilds();
        showLoggedPlayerInfo();

    }

    private void initComponents() {

    }

    private void initButtons() {

        guildsButton.addActionListener(e -> {
            loadGuilds();
        });

        membersButton.addActionListener(e -> {
            loadMembers();
        });

    }

    private void initFrame() {
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setTitle("Companion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    /**
     * Load known Guilds.
     */
    private void loadGuilds() {

        Guild[] guilds = Guild.getGuildsExtent().toArray(new Guild[0]);
        String[] columnNames = {"Guild Name", "Founder", "Members", "Created At"};
        changeTableModel(columnNames);

        mainTable.setModel(tableModel);

        for (Guild g : guilds) {

            tableModel.addRow(new String[]{g.getGuildName(), g.getFounderNickname(), g.getGuildMembers().size()
                    + "/99", String.valueOf(g.getDateOfCreation())});

        }

    }

    /** //TODO
     * Load known guild Members.
     */
    private void loadMembers() {

        if (Login.getLoggedUser().getGuild() == null) {
            JOptionPane.showMessageDialog(null, "You don't have a Guild!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Player[] players = Login.getLoggedUser().getGuild().getGuildMembers().toArray(new Player[0]);
        String[] columnNames = {"Nickname", "Level", "Day Message", "Awarded Rep", "Rank"};
        changeTableModel(columnNames);

        mainTable.setModel(tableModel);

        for (Player p : players) {

            tableModel.addRow(new String[]{p.getNickname(), String.valueOf(p.getLevel()),
                    String.valueOf(of(p.getMessageOfTheDay()).orElse(empty())),
                    String.valueOf(p.getReputationAwarded()), String.valueOf(p.getPlayerType())});

        }

    }

    /**
     * Show info about currently logged user.
     */
    private void showLoggedPlayerInfo() {
        loggedAsTextField.setText(Login.getLoggedUser().getNickname());
    }

    /**
     * Change Current JTable Model to show different data.
     */
    private void changeTableModel(String[] columnNames) {
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

}
