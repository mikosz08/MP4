
import gui.LoginGUI;
import serialization.DefaultDataManager;
import serialization.ExtentManager;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {

            ExtentManager.load();

        initDefaultData();

        setLookAndFeel();

        EventQueue.invokeLater(LoginGUI::new);

    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private static void initDefaultData() {

        DefaultDataManager.CreateDefaultData();

    }


}
