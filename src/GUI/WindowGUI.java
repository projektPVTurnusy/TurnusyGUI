package GUI;

import javax.swing.*;
import java.awt.*;

public class WindowGUI extends javax.swing.JFrame {

    private boolean addingEdge;
    private boolean addingNode;
    private boolean addingMode;
    private Container c;

    private JPanel mainPanel;
    private JButton addEdgeButton;
    private JPanel JPMenuBarr;
    private JPanel JPCanvas;
    private JPanel JPInfo;
    private JList listInfo;

    public WindowGUI() {
        setTitle("Drawing system");
        setMinimumSize(new Dimension(1600,800));
        setPreferredSize(new Dimension(1600,800));
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        add(mainPanel);
        mainPanel.setBackground(new Color(65, 65, 65));


        this.addingEdge = false;
        this.addingNode = false;
        this.addingMode = false;






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
