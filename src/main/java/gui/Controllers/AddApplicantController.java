package gui.Controllers;

import gui.Login;
import gui.MainGUI;
import gui.addAplicantGUI.AddApplicantGUI;
import models.functionalities.ApplicationForm;

import models.guild.Log;
import models.player.Player;
import serialization.ExtentManager;

import javax.swing.*;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class AddApplicantController {

    MainController mainController = new MainController();

    /**
     * Add accepted applicant
     */
    public void acceptApplicant(AddApplicantGUI addAplicantGUI, MainGUI mainGUI, int selectedRow, JTable mainTable) {

        if (selectedRow == -1) {
            return;
        }

        //get forms
        ApplicationForm[] applicationForms = getApplicationForms();

        //get player
        Player newPlayer = applicationForms[selectedRow].getPlayer();

        //add player to Guild
        Login.getLoggedUser().getGuild().addGuildMember(newPlayer);

        //delete player's form related to this guild.
        List<ApplicationForm> playerForms = newPlayer.getApplicationForms()
                .stream()
                .filter(x -> x.getGuild() == newPlayer.getGuild())
                .collect(toList());

        for (ApplicationForm ap : playerForms) {
            ap.delete();
        }

        mainController.printLog(String.valueOf(new Log(newPlayer.getNickname() + " joined Your guild!", Login.getLoggedUser().getGuild())));

        ExtentManager.save();

        mainController.loadGuilds(mainTable);
        mainGUI.setEnabled(true);
        addAplicantGUI.dispose();
    }

    /**
     * Remove declined application and save
     */
    public void declineApplicant(AddApplicantGUI addAplicantGUI, MainGUI mainGUI, int selectedRow) {

        if (selectedRow == -1) {
            return;
        }

        ApplicationForm[] applicationForms = getApplicationForms();
        applicationForms[selectedRow].delete();

        ExtentManager.save();

        mainGUI.setEnabled(true);
        addAplicantGUI.dispose();
    }

    /**
     * Show Player Info after double click on a row
     */
    public void showApplicantInfo(int selectedRow) {
        ApplicationForm[] applicationForms = getApplicationForms();
        JOptionPane.showMessageDialog(null,
                String.format("Nick: %s\nMessage: %s",
                        applicationForms[selectedRow].getPlayer().getNickname(),
                        applicationForms[selectedRow].getMessageContent())
                , "Applicant Message",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * returns current application forms connected to logged player's guild
     */
    private ApplicationForm[] getApplicationForms() {
        return Login.getLoggedUser().getGuild().getApplicationForms().toArray(new ApplicationForm[0]);
    }

}
