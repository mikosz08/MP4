package gui.Controllers;

import gui.CreateGuildGUI;
import gui.Login;
import gui.MainGUI;
import gui.RemoveApplication.ChooseGuildApplicationsGUI;
import gui.addAplicantGUI.AddAplicantGUI;
import gui.applyToGuildGUI.ChooseGuildToApplyGUI;
import models.functionalities.ApplicationForm;
import models.guild.Guild;
import models.player.Player;
import models.player.PlayerType;
import serialization.ExtentManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class MainController {

    DefaultTableModel tableModel;

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
            // TODO dodać CONST MAX PLAYERS
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

        Player[] players = Login.getLoggedUser().getGuild().getGuildMembers().toArray(new Player[0]);
        String[] columnNames = {"Nickname", "Level", "Day Message", "Awarded Rep", "Rank"};
        changeModel(columnNames);

        mainTable.setModel(tableModel);

        for (Player p : players) {

            tableModel.addRow(new String[]{p.getNickname(), String.valueOf(p.getLevel()),
                    p.getMessageOfTheDay().orElse("-"),
                    String.valueOf(p.getReputationAwarded()), String.valueOf(p.getPlayerType())});

        }
    }

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

        if (Login.getLoggedUser().getPlayerType() != PlayerType.GUILD_FOUNDER) {
            JOptionPane.showMessageDialog(null, "You can't do this!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Player owner = Login.getLoggedUser();
        Guild guild = owner.getGuild();

        guild.deleteGuild();

        loadGuilds(mainTable);
        showGuildInfo(guildInfoTextArea);
        ExtentManager.save();
    }

    /**
     * Apply To Guild
     *
     * @param mainGUI
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
    public void showAddApplicantDialog(MainGUI mainGUI) {
        if (Login.getLoggedUser().getPlayerType() == PlayerType.APPLICANT || Login.getLoggedUser().getPlayerType() == PlayerType.GUILD_MEMBER) {
            JOptionPane.showMessageDialog(null, "You need permission to do that!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        EventQueue.invokeLater(() -> new AddAplicantGUI(mainGUI));
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
     * @param mainGUI
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
