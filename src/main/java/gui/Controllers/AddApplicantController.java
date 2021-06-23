package gui.Controllers;

import gui.Login;
import gui.MainGUI;
import gui.addAplicantGUI.AddAplicantGUI;
import models.functionalities.ApplicationForm;

import models.player.Player;
import serialization.ExtentManager;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class AddApplicantController {

    public void acceptApplicant(AddAplicantGUI addAplicantGUI, MainGUI mainGUI, int selectedRow) {

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

        mainGUI.setEnabled(true);
        addAplicantGUI.dispose();
    }

    public void declineApplicant(AddAplicantGUI addAplicantGUI, MainGUI mainGUI, int selectedRow) {

        if(selectedRow == -1){
            mainGUI.setEnabled(true);
            addAplicantGUI.dispose();
            return;
        }

        ApplicationForm[] applicationForms = getApplicationForms();
        applicationForms[selectedRow].delete();

        ExtentManager.save();

        mainGUI.setEnabled(true);
        addAplicantGUI.dispose();
    }

    /*
     * Show Player Info after double click on a row
     * */
    public void showApplicantInfo(int selectedRow) {
        ApplicationForm[] applicationForms = getApplicationForms();
        JOptionPane.showMessageDialog(null,
                String.format("Nick: %s\nMessage: %s",
                        applicationForms[selectedRow].getPlayer().getNickname(),
                        applicationForms[selectedRow].getMessageContent())
                , "Applicant Message",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /*
     * returns current application forms connected to logged player's guild
     * */
    private ApplicationForm[] getApplicationForms() {
        return Login.getLoggedUser().getGuild().getApplicationForms().toArray(new ApplicationForm[0]);
    }

}
