package gui.Controllers;

import gui.CreateGuildGUI;
import gui.Login;
import gui.MainGUI;
import models.guild.Faction;
import models.guild.Guild;
import models.guild.Log;
import models.player.Player;
import serialization.ExtentManager;

import javax.swing.*;

public class CreateGuildController {
    MainController mainController = new MainController();

    /**
     * Create Guild.
     */
    public Guild createGuild(String guildName, String factionName, CreateGuildGUI createGuildGUI,
                             MainGUI mainGUI, JTable mainTable, JTextArea guildInfoTextArea) {
        Player owner = Login.getLoggedUser();
        Faction faction = null;

        for (Faction f : Faction.getFactionsExtent()) {
            if (f.getFactionName().equals(factionName)) {
                faction = f;
            }
        }

        Login.getLoggedUser().becomeGuildFounder();
        Guild createdGuild = new Guild(guildName, owner, faction);

        mainController.printLog(String.valueOf(new Log("Congratulations, You created a Guild!",
                Login.getLoggedUser().getGuild())));

        ExtentManager.save();

        mainController.showGuildInfo(createdGuild, guildInfoTextArea);
        mainController.loadGuilds(mainTable);

        mainGUI.setEnabled(true);
        createGuildGUI.dispose();

        return createdGuild;
    }

    /**
     * Close Dialog.
     */
    public void onCancel(CreateGuildGUI createGuildGUI, MainGUI mainGUI) {
        mainGUI.setEnabled(true);
        createGuildGUI.dispose();
    }

}
