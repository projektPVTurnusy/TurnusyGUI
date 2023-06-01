/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import Data.*;

import javax.swing.*;


public class Canvas extends JComponent {

    private final double startX = 0;
    private final double startY = 0;
    private DataManager dataManager;
    private Graphics2D g2d;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent (g);
        this.g2d = (Graphics2D) g;



        // edges
        if (dataManager == null) {
            return;
        }
        ArrayList<Edge> edges = dataManager.getEdges();
        for (Edge edge : edges) {
            this.g2d.setColor(Color.BLUE);
            Node node1 = edge.getNode1();
            Node node2 = edge.getNode2();

            if (edge == this.dataManager.getSelectedEdge()) {
                this.g2d.setColor(Color.GREEN);
            } else {
                this.g2d.setColor(Color.LIGHT_GRAY);
            }
            this.g2d.setStroke(new BasicStroke((float) dataManager.getEdgeSize()));
            this.g2d.drawLine((int)(node1.getX() + startX), (int)(node1.getY() + startY), (int)(node2.getX() + startX), (int)(node2.getY() + startY));

        }

        // node
        ArrayList<Node> nodes = dataManager.getNodes();
        int offset = dataManager.getNodeSize() / 2;
        for (Node node : nodes) {
            if (node == this.dataManager.getSelectedNode()) {
                this.g2d.setColor(Color.GREEN);
                System.out.println("prekreslenie");
            } else {
                switch (node.getType()) {
                    case CUSTOMER:
                        this.g2d.setColor(Color.YELLOW);
                        break;
                    case WAREHOUSE_SLOT:
                        this.g2d.setColor(Color.ORANGE);
                        break;
                    default:
                        this.g2d.setColor(Color.BLUE);
                        break;
                }
            }
            this.g2d.fillOval((int)(node.getX() - offset + startX), (int)(node.getY() - offset + startY), dataManager.getNodeSize(), dataManager.getNodeSize());
        }
    }

    public void redraw() {
        this.repaint();
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        repaint();
    }


}
