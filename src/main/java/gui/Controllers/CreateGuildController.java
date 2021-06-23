package gui.Controllers;

import gui.CreateGuildGUI;
import gui.Login;
import models.guild.Faction;
import models.guild.Guild;
import models.player.Player;


public class CreateGuildController {

    /**
     * Create Guild.
     */
    public Guild createGuild(String guildName, String factionName, CreateGuildGUI createGuildGUI) {
        Player owner = Login.getLoggedUser();
        Faction faction = null;

        for (Faction f : Faction.getFactionsExtent()) {
            if (f.getFactionName().equals(factionName)) {
                faction = f;
            }
        }

        Login.getLoggedUser().becomeGuildFounder();
        Guild g = new Guild(guildName, owner, faction);

        createGuildGUI.dispose();
        return g;
    }

    public void onCancel(CreateGuildGUI createGuildGUI) {
        createGuildGUI.dispose();
    }

}
