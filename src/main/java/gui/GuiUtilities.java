package gui;

import javax.swing.*;

public abstract class GuiUtilities extends JDialog {

    public void OnExit(JFrame frame){
        System.out.println("exit");
        frame.setEnabled(true);
        this.dispose();
    }

}
