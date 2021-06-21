package gui;

import models.player.Player;

public class Login {

    private Player loggedUser;

    public Login(Player loggedUser) {
        setLoggedUser(loggedUser);
    }

    public Player getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Player loggedUser) {
        this.loggedUser = loggedUser;
    }
}
