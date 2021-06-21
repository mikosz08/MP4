import gui.LoginGUI;
import serialization.DefaultDataManager;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        initDefaultData();
        EventQueue.invokeLater(LoginGUI::new);

    }

    private static void initDefaultData() {

        DefaultDataManager.CreateDefaultGuilds();

    }



}
