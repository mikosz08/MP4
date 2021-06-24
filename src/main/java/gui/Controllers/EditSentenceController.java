package gui.Controllers;

import gui.EditDaySentenceGUI;
import gui.Login;
import gui.MainGUI;
import models.exception.DataValidationException;
import models.guild.Log;
import serialization.ExtentManager;

import javax.swing.*;

public class EditSentenceController {

    MainController mainController = new MainController();

    public void changeSentence(EditDaySentenceGUI editSentenceGUI, MainGUI mainGUI, String text) {

        Login.getLoggedUser().setSentenceOfTheDay(text);

        if(text != null && !text.trim().isBlank()){
            mainController.printLog(String.valueOf(new Log(Login.getLoggedUser().getNickname() + " has changed day sentence: " +
                    text, Login.getLoggedUser().getGuild())));
        }

        ExtentManager.save();
        mainGUI.setEnabled(true);
        editSentenceGUI.dispose();
    }

    public void closeEditSentenceDialog(EditDaySentenceGUI editSentenceGUI, MainGUI mainGUI) {

        mainGUI.setEnabled(true);
        editSentenceGUI.dispose();
    }

}
