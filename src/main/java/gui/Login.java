package gui;

import models.player.Player;

public class Login {

    private static Player loggedUser;

    public Login(Player loggedUser) {
        setLoggedUser(loggedUser);
    }

    public static Player getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(Player loggedUser) {
        Login.loggedUser = loggedUser;
    }
}
