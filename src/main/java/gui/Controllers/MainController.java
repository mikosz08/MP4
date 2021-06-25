package gui.Controllers;

import gui.*;
import gui.removeApplication.ChooseGuildApplicationsGUI;
import gui.addAplicantGUI.AddApplicantGUI;
import gui.applyToGuildGUI.ChooseGuildToApplyGUI;
import models.functionalities.ApplicationForm;
import models.guild.Guild;
import models.player.Player;
import models.player.PlayerType;
import serialization.ExtentManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private DefaultTableModel tableModel;

    private static JTextArea logArea;

    public MainController() {
    }

    /**
     * Load known Guilds.
     */
    public void loadGuilds(JTable mainTable) {

        Guild[] guilds = Guild.getGuildsExtent().toArray(new Guild[0]);
        String[] columnNames = {"Guild Name", "Founder", "Members", "Created At"};
        changeModel(columnNames);

        mainTable.setModel(tableModel);

        for (Guild g : guilds) {
            tableModel.addRow(new String[]{g.getGuildName(), g.getFounderNickname(), g.getGuildMembers().size()
                    + "/99", String.valueOf(g.getDateOfCreation())});

        }

    }

    /**
     * Load known guild Members.
     */
    public void loadMembers(JTable mainTable) {

        if (Login.getLoggedUser().getGuild() == null) {
            JOptionPane.showMessageDialog(null, "You don't have a Guild!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<Player> players = new ArrayList<>(Login.getLoggedUser().getGuild().getGuildMembers());

        String[] columnNames = {"Nickname", "Level", "Day Message", "Awarded Rep", "Rank"};
        changeModel(columnNames);

        mainTable.setModel(tableModel);

        for (Player p : players) {

            tableModel.addRow(new String[]{p.getNickname(), String.valueOf(p.getLevel()),
                    p.getMessageOfTheDay().orElse(""),
                    String.valueOf(p.getReputationAwarded()), String.valueOf(p.getPlayerType())});

        }
    }

    /**
     * Load known guild Applicants.
     */
    public void loadApplications(JTable table, Guild choosedGuild) {

        String[] columnNames = {"Your Applications", "Message"};
        changeModel(columnNames);
        table.setModel(tableModel);

        for (ApplicationForm ap : choosedGuild.getApplicationForms()) {

            tableModel.addRow(new String[]{
                    "Send at: " + ap.getMessagePostDate(),
                    ap.getMessageContent()
            });
        }
    }

    /**
     * Shows Create Guild Dialog Window.
     *
     * @param mainGUI           - main frame
     * @param guildInfoTextArea guild info area
     * @param mainTable         - guilds table
     */
    public void showCreateGuildDialog(MainGUI mainGUI, JTextArea guildInfoTextArea, JTable mainTable) {
        if (Login.getLoggedUser().getPlayerType() != PlayerType.APPLICANT) {
            JOptionPane.showMessageDialog(null, "You already have a guild!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        EventQueue.invokeLater(() -> new CreateGuildGUI(mainGUI, guildInfoTextArea, mainTable));
        mainGUI.setEnabled(false);
    }

    /**
     * Show info about currently logged user.
     */
    public void showLoggedPlayerInfo(JTextField loggedAsTextField) {
        loggedAsTextField.setText(Login.getLoggedUser().getNickname());
    }

    /**
     * Change Current JTable Model to show different data.
     */
    public void changeModel(String[] columnNames) {
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    /**
     * Remove Guild from existence
     */
    public void deleteGuild(JTable mainTable, JTextArea guildInfoTextArea) {

        if (Login.getLoggedUser().getPlayerType() != PlayerType.FOUNDER) {
            JOptionPane.showMessageDialog(null, "You can't do this!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(null, "Delete Guild?", "Warning",
                JOptionPane.YES_NO_OPTION) != 0) {
            return;
        }

        Player owner = Login.getLoggedUser();
        Guild guild = owner.getGuild();

        guild.deleteGuild();

        ExtentManager.save();

        loadGuilds(mainTable);
        showGuildInfo(guildInfoTextArea);
    }

    /**
     * Leave current Guild
     *
     * @param mainTable         - main frame window
     * @param guildInfoTextArea - area to display info messages
     */
    public void leaveGuild(JTable mainTable, JTextArea guildInfoTextArea) {

        if (Login.getLoggedUser().getGuild() == null) {
            JOptionPane.showMessageDialog(null, "You don't have a guild!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        } else if (Login.getLoggedUser().getGuild().getGuildMembers().size() == 1) {
            deleteGuild(mainTable, guildInfoTextArea);
            return;
        }

        Player player = Login.getLoggedUser();
        Guild playerGuild = player.getGuild();
        Player nextFounder = null;

        if (JOptionPane.showConfirmDialog(null, "Leave Guild?", "Warning",
                JOptionPane.YES_NO_OPTION) != 0) {
            return;
        }


        if (player.getPlayerType() == PlayerType.FOUNDER) {
            player.abandonGuild();

            //next officer becomes founder
            boolean nextFounderFound = false;
            for (Player member : playerGuild.getGuildMembers()) {
                if (member.getPlayerType() == PlayerType.OFFICER) {
                    nextFounder = member;
                    nextFounderFound = true;
                }
            }

            //if there is no officers, next member becomes founder
            if (!nextFounderFound) {
                nextFounder = playerGuild.getGuildMembers().iterator().next();
            }

            nextFounder.becomeGuildFounder();
            playerGuild.setFounderNickname(nextFounder.getNickname());

        } else {
            player.abandonGuild();
        }

        ExtentManager.save();

        loadGuilds(mainTable);
        showGuildInfo(guildInfoTextArea);
    }

    /**
     * Apply To Guild
     *
     * @param mainGUI - main frame window
     */
    public void showChooseGuildDialog(MainGUI mainGUI) {
        if (Login.getLoggedUser().getPlayerType() != PlayerType.APPLICANT) {
            JOptionPane.showMessageDialog(null, "You already have a guild!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        EventQueue.invokeLater(() -> new ChooseGuildToApplyGUI(mainGUI));
        mainGUI.setEnabled(false);
    }

    /**
     * Load dialog with Guild applicants.
     */
    public void showAddApplicantDialog(MainGUI mainGUI, JTable mainTable) {
        if (Login.getLoggedUser().getPlayerType() == PlayerType.APPLICANT || Login.getLoggedUser().getPlayerType() == PlayerType.MEMBER) {
            JOptionPane.showMessageDialog(null, "You need permission to do that!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        EventQueue.invokeLater(() -> new AddApplicantGUI(mainGUI, mainTable));
        mainGUI.setEnabled(false);
    }

    /**
     * Load guild Applicants.
     */
    public void loadApplicants(JTable applicantsTable) {

        ApplicationForm[] applicationForms = Login.getLoggedUser().getGuild().getApplicationForms().toArray(new ApplicationForm[0]);
        String[] columnNames = {"From"};
        changeModel(columnNames);

        applicantsTable.setModel(tableModel);
        for (ApplicationForm ap : applicationForms) {
            tableModel.addRow(new String[]{ap.getPlayer().getNickname()});
        }

    }

    /**
     * Load Dialog to select the guild from which you want to remove the application.
     *
     * @param mainGUI - main frame window
     */
    public void showRemoveApplicationDialog(MainGUI mainGUI) {

        if (Login.getLoggedUser().getPlayerType() != PlayerType.APPLICANT) {
            JOptionPane.showMessageDialog(null, "You can't do that right now!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        mainGUI.setEnabled(false);
        EventQueue.invokeLater(() -> new ChooseGuildApplicationsGUI(mainGUI));
    }

    /**
     * Load Dialog to edit sentence of the day.
     *
     * @param mainGUI - main frame window
     */
    public void editDaySentence(MainGUI mainGUI) {

        if (Login.getLoggedUser().getPlayerType() != PlayerType.FOUNDER) {
            JOptionPane.showMessageDialog(null, "You don't have permissions to do that!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        mainGUI.setEnabled(false);
        EventQueue.invokeLater(() -> new EditDaySentenceGUI(mainGUI));

    }

    /**
     * Load Dialog to select guild member to kick.
     */
    public void showKickMemberDialog(MainGUI mainGUI, JTable mainTable) {

        if (Login.getLoggedUser().getPlayerType() != PlayerType.FOUNDER) {
            JOptionPane.showMessageDialog(null, "You don't have permissions to do that!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        mainGUI.setEnabled(false);
        EventQueue.invokeLater(() -> new KickMemberGUI(mainGUI, mainTable));

    }

    /**
     * Load Dialog to edit message of the day.
     *
     * @param mainGUI - main frame window
     */
    public void editDayMessage(MainGUI mainGUI, JTable mainTable) {
        if (Login.getLoggedUser().getPlayerType() == PlayerType.APPLICANT) {
            JOptionPane.showMessageDialog(null, "You don't have permissions to do that!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        mainGUI.setEnabled(false);
        EventQueue.invokeLater(() -> new EditDayMessageGUI(mainGUI, mainTable));
    }

    /**
     * Load Dialog to show player's ranking.
     */
    public void showRankingDialog(MainGUI mainGUI) {

        if (Login.getLoggedUser().getPlayerType() == PlayerType.APPLICANT) {
            JOptionPane.showMessageDialog(null, "You don't have permissions to do that!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        mainGUI.setEnabled(false);
        EventQueue.invokeLater(() -> new RankingGUI(mainGUI));

    }

    /**
     * Print Log
     */
    public void printLog(String message) {
        logArea.append(message + "\n");
    }

    public void setLogArea(JTextArea mainLogArea) {
        logArea = mainLogArea;
    }

    public void showGuildInfo(Guild info, JTextArea guildInfoTextArea) {

        guildInfoTextArea.setText(
                String.format("GUILD INFO\n" +
                                "%s\n" +
                                "%s\n" +
                                "%s\n",
                        info.getGuildName(),
                        info.getFaction().getFactionName(),
                        info.getDateOfCreation())
        );
    }

    public void showGuildInfo(JTextArea guildInfoTextArea) {

        guildInfoTextArea.setText(
                String.format("GUILD INFO\n" +
                        "Name: ---\n" +
                        "Faction: ---\n" +
                        "Created At: ---\n")
        );
    }


}
