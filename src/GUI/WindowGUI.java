package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowGUI extends javax.swing.JFrame {

    private boolean addingEdge;
    private boolean addingNode;
    private boolean addingMode;

    private JTabbedPane tabbedPane1;
    private JPanel JPanelDraw;
    private JButton loadDataButton;
    private JButton saveDataButton;
    private JPanel JPanelToolMenu;
    private JButton addEdgeButton;
    private JButton AddNodeButton;
    private JPanel mainPanel;
    private JPanel JPanelInfo;

    public WindowGUI() {
        setTitle("Drawing system");
        setMinimumSize(new Dimension(800,500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainPanel.setVisible(true);
        add(mainPanel);


        this.JPanelDraw = new Canvas();
        this.addingEdge = false;
        this.addingNode = false;
        this.addingMode = false;


        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addingNode) {
                    addingNode = false;
                }

                if (addingEdge) {
                    addingEdge = false;
                    addingMode = false;

                } else {
                    addingEdge = true;
                    addingMode = true;
                }
                changeCursor();
                // TODO vytvaranie Node


            }
        });
        AddNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addingEdge) {
                    addingEdge = false;
                }

                if (addingNode) {
                    addingNode = false;
                    addingMode = false;

                } else {
                    addingNode = true;
                    addingMode = true;
                }
                changeCursor();
                // TODO vytvaranie Edge
            }
        });
    };


    /*
    * Zmena cursora nech je viditelne ze sa vytvara daky objekt napr node alebo edge
    * */
    private void changeCursor() {
        if (this.addingMode) {
            setCursor(Cursor.getDefaultCursor());
        } else {
            setCursor( Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }
}
