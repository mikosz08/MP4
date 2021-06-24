package gui.Controllers;

import gui.MainGUI;
import gui.removeApplication.ChooseApplicationToDeleteGUI;
import gui.removeApplication.ChooseGuildApplicationsGUI;
import models.functionalities.ApplicationForm;
import models.guild.Guild;
import serialization.ExtentManager;

import javax.swing.*;
import java.awt.*;

public class RemoveApplicationController {



    /**
     * Chose Guild
     */
    public void chooseGuild(ChooseGuildApplicationsGUI applicationChooseGuildGUI, MainGUI mainGUI, int selectedRow) {

        if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Choose a Guild!", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Guild selectedGuild = Guild.getGuildsExtent().get(selectedRow);

        EventQueue.invokeLater(() -> new ChooseApplicationToDeleteGUI(selectedGuild, mainGUI));

        mainGUI.setEnabled(true);
        applicationChooseGuildGUI.dispose();
    }

    public void closeChooseGuildGUI(ChooseGuildApplicationsGUI applicationChooseGuildGUI, MainGUI mainGUI) {
        mainGUI.setEnabled(true);
        applicationChooseGuildGUI.dispose();
    }

    /**
     * Choose Application to remove
     */
    public void deleteChoosedApplication(ChooseApplicationToDeleteGUI chooseApplicationToDeleteGUI, MainGUI mainGUI, int selectedRow) {

        if(selectedRow == -1){
            JOptionPane.showMessageDialog(null, "Choose an Application!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ApplicationForm.getApplicationFormsExtent().get(selectedRow).delete();

        JOptionPane.showMessageDialog(null, "Application Form removed!", "Info",
                JOptionPane.INFORMATION_MESSAGE);

        ExtentManager.save();
        mainGUI.setEnabled(true);
        chooseApplicationToDeleteGUI.dispose();
    }

    public void closeRemoveApplicationGUI(ChooseApplicationToDeleteGUI chooseApplicationToDeleteGUI, MainGUI mainGUI) {
        mainGUI.setEnabled(true);
        chooseApplicationToDeleteGUI.dispose();
    }



}
