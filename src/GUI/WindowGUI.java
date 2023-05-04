package GUI;

import Main.Main;

import javax.swing.*;
import java.awt.*;

public class WindowGUI extends javax.swing.JFrame {

    private Main main;
    private JTabbedPane tabbedPane1;
    private JPanel JPanelDraw;
    private JButton loadDataButton;
    private JButton saveDataButton;
    private JPanel JPanelToolMenu;

    public WindowGUI(Main main) {
        this.main = main;
        this.setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 500));
        setPreferredSize(new java.awt.Dimension(800, 500));
        setBackground(new Color(91, 91, 91));

        this.JPanelDraw = new Canvas();

        JPanelToolMenu.setSize(50,800);
        JPanelToolMenu.setBackground(new Color(90,90,90));

    };


}
