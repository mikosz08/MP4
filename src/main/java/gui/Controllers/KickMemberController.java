package gui.Controllers;

import gui.KickMemberGUI;
import gui.Login;
import gui.MainGUI;
import models.player.Player;
import serialization.ExtentManager;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class KickMemberController {

    MainController mainController = new MainController();

    public void kickPlayer(KickMemberGUI kickMemberGUI, MainGUI mainGUI, int selectedRow, JTable mainTable) {

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "You need to choose a member first!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Player playerToKick = new ArrayList<>(Login.getLoggedUser().getGuild().getGuildMembers()).get(selectedRow);

        if (playerToKick.equals(Login.getLoggedUser())) {
            JOptionPane.showMessageDialog(null, "You can't kick yourself!", "Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Login.getLoggedUser().getGuild().removeGuildMember(playerToKick);

        ExtentManager.save();

        mainController.loadMembers(mainTable);
        mainGUI.setEnabled(true);
        kickMemberGUI.dispose();
    }

    public void closeKickMemberDialog(KickMemberGUI kickMemberGUI, MainGUI mainGUI) {

        mainGUI.setEnabled(true);
        kickMemberGUI.dispose();
    }

}
