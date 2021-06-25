package gui;

import javax.swing.*;

import gui.Controllers.EditSentenceController;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditDaySentenceGUI extends JDialog {
    private JPanel contentPane;
    private JButton doneButton;
    private JButton cancelButton;
    private JTextField newDaySentenceJText;

    EditSentenceController editSentenceController;

    public EditDaySentenceGUI(MainGUI mainGUI) {
        editSentenceController = new EditSentenceController();

        initFrame();
        initButtons(mainGUI);
    }

    private void initButtons(MainGUI mainGUI) {
        doneButton.addActionListener(e -> {
            editSentenceController.changeSentence(this, mainGUI, newDaySentenceJText.getText());
        });

        cancelButton.addActionListener(e -> {
            editSentenceController.closeEditSentenceDialog(this, mainGUI);
        });

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
        setTitle("Edit Sentence");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

}

