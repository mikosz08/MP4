package gui.Controllers;

import gui.CreateGuildGUI;
import gui.Login;
import gui.MainGUI;
import models.functionalities.ApplicationForm;
import models.guild.Guild;
import models.player.Player;
import models.player.PlayerType;
import serialization.ExtentManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

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
        changeTableModel(columnNames);

        mainTable.setModel(tableModel);

        for (Guild g : guilds) {

            tableModel.addRow(new String[]{g.getGuildName(), g.getFounderNickname(), g.getGuildMembers().size()
                    + "/99", String.valueOf(g.getDateOfCreation())});

        }

    }

    /**
     * //TODO: Check this.
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
        changeTableModel(columnNames);

        mainTable.setModel(tableModel);

        for (Player p : players) {

            tableModel.addRow(new String[]{p.getNickname(), String.valueOf(p.getLevel()),
                    String.valueOf(p.getMessageOfTheDay().orElse("-")),
                    String.valueOf(p.getReputationAwarded()), String.valueOf(p.getPlayerType())});

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
    public void changeTableModel(String[] columnNames) {
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    //Remove Guild from existence
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
