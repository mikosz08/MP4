package gui.addAplicantGUI;

import gui.Controllers.AddApplicantController;
import gui.Controllers.MainController;
import gui.MainGUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddAplicantGUI extends JDialog {
    private JPanel contentPane;
    private JButton acceptButton;
    private JButton declineButton;
    private JTable applicantsJTable;

    MainController mainController;
    AddApplicantController addApplicantController;

    public AddAplicantGUI(MainGUI mainGUI) {
        mainController = new MainController();
        addApplicantController = new AddApplicantController();

        initFrame();
        initButtons(mainGUI);

        mainController.loadApplicants(applicantsJTable);

    }

    private void initButtons(MainGUI mainGUI) {
        acceptButton.addActionListener(e -> {
            addApplicantController.acceptApplicant(this, mainGUI, applicantsJTable.getSelectedRow());
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
