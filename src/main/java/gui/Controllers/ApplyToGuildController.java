package gui.Controllers;

import gui.Login;
import gui.MainGUI;
import gui.applyToGuildGUI.ApplicationFormGUI;
import gui.applyToGuildGUI.ChooseGuildToApplyGUI;
import models.functionalities.ApplicationForm;
import models.guild.Guild;
import models.guild.Log;
import models.player.Player;
import serialization.ExtentManager;

import java.awt.*;

public class ApplyToGuildController {

    MainController mainController = new MainController();

    /**
     * Open apply to guild dialog
     */
    public void showApplyToGuildDialog(ChooseGuildToApplyGUI chooseGuildGUI, MainGUI mainGUI, Guild choosedGuild) {
        mainGUI.setEnabled(true);
        EventQueue.invokeLater(() -> new ApplicationFormGUI(mainGUI, choosedGuild));
        chooseGuildGUI.dispose();
    }

    /**
     * Close choose guild dialog
     */
    public void closeChooseGuildGUI(ChooseGuildToApplyGUI chooseGuildGUI, MainGUI mainGUI) {
        mainGUI.setEnabled(true);
        chooseGuildGUI.dispose();
    }

    /**
     * Send created application
     */
    public void sendApplication(ApplicationFormGUI applicationFormGUI, MainGUI mainGUI, Guild choosedGuild, String text) {
        Player player = Login.getLoggedUser();

        new ApplicationForm(text, choosedGuild, player);

        mainController.printLog(String.valueOf(new Log("Application to: " + choosedGuild.getGuildName() +
                " has been send!", choosedGuild)));

        ExtentManager.save();

        mainGUI.setEnabled(true);
        applicationFormGUI.dispose();
    }

    public void closeApplicationFormDialog(ApplicationFormGUI applicationFormGUI, MainGUI mainGUI) {
        mainGUI.setEnabled(true);
        applicationFormGUI.dispose();
    }

}
