package gui;

import gui.Controllers.MainController;
import models.guild.Guild;
import javax.swing.*;

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
    private JButton leaveGuildButton;

    MainController mainController;

    public MainGUI() {
        mainController = new MainController();

        initFrame();
        initButtons();

        mainController.loadGuilds(mainTable);
        mainController.showLoggedPlayerInfo(loggedAsTextField);
        mainController.setLogArea(logTextArea);

        showGuildInfo();
    }

    /**
     * Initialize MainFrame.
     */
    private void initFrame() {
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setTitle("Companion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private void initButtons() {

        //Show Guilds Button
        guildsButton.addActionListener(e -> {
            mainController.loadGuilds(mainTable);
        });

        //Show guild Members Button
        membersButton.addActionListener(e -> mainController.loadMembers(mainTable));

        //Create Guild Button
        createGuildButton.addActionListener(e -> mainController.showCreateGuildDialog(this, guildInfoTextArea, mainTable));

        //Delete Guild Button
        deleteGuildButton.addActionListener(e -> mainController.deleteGuild(mainTable, guildInfoTextArea));

        //Apply To Guild Button
        applyToGuildButton.addActionListener(e -> mainController.showChooseGuildDialog(this));

        //Add Applicant Button
        addApplicantButton.addActionListener(e -> mainController.showAddApplicantDialog(this, mainTable));

        //Remove Application Button
        removeApplicationButton.addActionListener(e -> mainController.showRemoveApplicationDialog(this));

        //Edit Day Sentence Button
        editDaySentenceButton.addActionListener(e -> mainController.editDaySentence(this));

        //Kick Member Button
        kickMemberButton.addActionListener(e -> mainController.showKickMemberDialog(this, mainTable));

        //View Ranking Button
        viewRankingButton.addActionListener(e -> mainController.showRankingDialog(this));

        //Edit Day Message Button
        editDayMessageButton.addActionListener(e -> mainController.editDayMessage(this, mainTable));

        //Leave Guild Button
        leaveGuildButton.addActionListener(e -> mainController.leaveGuild(mainTable, guildInfoTextArea));
    }

    private void showGuildInfo() {
        Guild guild = Login.getLoggedUser().getGuild();
        if (guild != null) {
            mainController.showGuildInfo(guild, guildInfoTextArea);
        }
    }

}
