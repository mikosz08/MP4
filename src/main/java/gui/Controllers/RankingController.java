package gui.Controllers;

import gui.Login;
import gui.MainGUI;
import gui.RankingGUI;
import models.player.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RankingController {

    DefaultTableModel tableModel;

    /**
     * Show Ranking based on player's level.
     */
    public void showRankingByLevel(JTable rankingTable) {

        String[] columnNames = {"Nickname", "Level"};
        changeModel(columnNames);
        rankingTable.setModel(tableModel);

        for (Player p : Player.getRankingByLevel()) {
            if (p.getGuild() != null && p.getGuild().equals(Login.getLoggedUser().getGuild())) {

                tableModel.addRow(new String[]{
                        p.getNickname(), String.valueOf(p.getLevel())
                });

            }
        }

    }

    /**
     * Show Ranking based on player's reputation awarded.
     */
    public void showRankingByReputation(JTable rankingTable) {


        String[] columnNames = {"Nickname", "Reputation"};
        changeModel(columnNames);
        rankingTable.setModel(tableModel);

        for (Player p : Player.getRankingByRepPoints()) {
            if (p.getGuild() != null && p.getGuild().equals(Login.getLoggedUser().getGuild())) {

                tableModel.addRow(new String[]{
                        p.getNickname(), String.format("%.2f", p.getReputationAwarded())
                });

            }
        }

    }

    /**
     * Close Dialog.
     *
     */
    public void closeRankingDialog(RankingGUI rankingGUI, MainGUI mainGUI) {

        mainGUI.setEnabled(true);
        rankingGUI.dispose();
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

}
