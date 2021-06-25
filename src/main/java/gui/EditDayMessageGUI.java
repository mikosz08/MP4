package gui;

import gui.Controllers.EditMessageController;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditDayMessageGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField newDayMessageJText;

    EditMessageController editMessageController;

    public EditDayMessageGUI(MainGUI mainGUI, JTable mainTable) {
        editMessageController = new EditMessageController();

        initFrame();
        initButtons(mainGUI, mainTable);
    }

    private void initButtons(MainGUI mainGUI, JTable mainTable) {
        buttonOK.addActionListener(e -> editMessageController.changeMessage(this, mainGUI, newDayMessageJText.getText(), mainTable));

        buttonCancel.addActionListener(e -> editMessageController.closeEditMessageDialog(this, mainGUI));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainGUI.setEnabled(true);
                dispose();
            }
        });
    }

    private void initFrame() {
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setTitle("Edit Message");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

}
