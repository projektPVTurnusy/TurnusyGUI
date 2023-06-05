/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Data.DataManager;
import Data.Edge;
import Data.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Canvas extends JComponent {

    private double startX;
    private double startY;
    private double scale;
    private DataManager dataManager;
    private Graphics2D g2d;

    public Canvas() {
        this.startX = 0;
        this.startY = 0;
        this.scale = 1;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

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
            this.g2d.drawLine((int)(node1.getX() + this.startX), (int)(node1.getY() + this.startY),
                    (int)(node2.getX() + this.startX), (int)(node2.getY() + this.startY));
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
            this.g2d.fillOval((int)(node.getX() - offset + this.startX), (int)(node.getY() - offset + this.startY),
                    dataManager.getNodeSize(), dataManager.getNodeSize());
        }
    }

    public void redraw() {
        this.repaint();
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        repaint();
    }

    private boolean isWithinBounds(double x, double y) {
        return (((x < this.startX + getWidth()) && (x > this.startX)) &&
                ((y < this.startY + getHeight()) && (y > this.startY)));
    }
}
