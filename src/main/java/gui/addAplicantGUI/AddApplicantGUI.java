package gui.addAplicantGUI;

import gui.Controllers.AddApplicantController;
import gui.Controllers.MainController;
import gui.MainGUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddApplicantGUI extends JDialog {
    private JPanel contentPane;
    private JButton acceptButton;
    private JButton declineButton;
    private JTable applicantsJTable;

    MainController mainController;
    AddApplicantController addApplicantController;

    public AddApplicantGUI(MainGUI mainGUI, JTable mainTable) {
        mainController = new MainController();
        addApplicantController = new AddApplicantController();

        initFrame();
        initButtons(mainGUI, mainTable);

        mainController.loadApplicants(applicantsJTable);
    }

    private void initButtons(MainGUI mainGUI, JTable mainTable) {
        acceptButton.addActionListener(e -> {
            addApplicantController.acceptApplicant(this, mainGUI, applicantsJTable.getSelectedRow(), mainTable);
        });

        declineButton.addActionListener(e -> {
            addApplicantController.declineApplicant(this, mainGUI, applicantsJTable.getSelectedRow());
        });

        applicantsJTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addApplicantController.showApplicantInfo(applicantsJTable.getSelectedRow());
            }
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
        setTitle("Add Applicant");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }


}
