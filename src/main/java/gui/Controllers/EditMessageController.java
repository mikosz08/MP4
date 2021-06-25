package gui.Controllers;


import gui.EditDayMessageGUI;
import gui.Login;
import gui.MainGUI;
import models.guild.Log;
import serialization.ExtentManager;

import javax.swing.*;

public class EditMessageController {
    MainController mainController = new MainController();

    /**
     * Change Day Message.
     */
    public void changeMessage(EditDayMessageGUI editDayMessageGUI, MainGUI mainGUI, String text, JTable mainTable) {
        Login.getLoggedUser().setMessageOfTheDay(text);

        if (text != null && !text.trim().isBlank()) {
            mainController.printLog(String.valueOf(new Log(Login.getLoggedUser().getNickname() + " has changed day message: " +
                    text, Login.getLoggedUser().getGuild())));
        }

        mainController.loadMembers(mainTable);

        ExtentManager.save();
        mainGUI.setEnabled(true);
        editDayMessageGUI.dispose();
    }

    /**
     * Close Dialog.
     */
    public void closeEditMessageDialog(EditDayMessageGUI editDayMessageGUI, MainGUI mainGUI) {
        mainGUI.setEnabled(true);
        editDayMessageGUI.dispose();
    }

}
