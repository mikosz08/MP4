package gui.Controllers;

import gui.Login;
import gui.MainGUI;
import gui.applyToGuildGUI.ApplicationFormGUI;
import gui.applyToGuildGUI.ChooseGuildToApplyGUI;
import models.exception.DataValidationException;
import models.functionalities.ApplicationForm;
import models.guild.Guild;
import models.player.Player;
import serialization.ExtentManager;

import javax.swing.*;
import java.awt.*;

public class ApplyToGuildController {

    public void chooseGuild(ChooseGuildToApplyGUI chooseGuildGUI, MainGUI mainGUI, Guild choosedGuild) {
        mainGUI.setEnabled(true);
        EventQueue.invokeLater(() -> new ApplicationFormGUI(mainGUI, choosedGuild));
        chooseGuildGUI.dispose();
    }

    public void closeChooseGuildGUI(ChooseGuildToApplyGUI chooseGuildGUI, MainGUI mainGUI) {
        mainGUI.setEnabled(true);
        chooseGuildGUI.dispose();
    }

    public void sendApplication(ApplicationFormGUI applicationFormGUI, Guild choosedGuild, String text) {
        Player player = Login.getLoggedUser();
        try {
           ApplicationForm a = new ApplicationForm(text, choosedGuild, player);
        }catch (DataValidationException ex){
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Application Send!", "Info",
                JOptionPane.INFORMATION_MESSAGE);

        ExtentManager.save();
        applicationFormGUI.dispose();
    }

    public void closeApplicationFormGUI(ApplicationFormGUI applicationFormGUI) {

        applicationFormGUI.dispose();
    }

}
