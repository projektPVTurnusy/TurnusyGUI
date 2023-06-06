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
    private double zoomScale;
    private DataManager dataManager;
    private Graphics2D g2d;

    public Canvas() {
        this.startX = 0;
        this.startY = 0;
        this.zoomScale = 1;
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
            this.g2d.drawLine((int)((node1.getX() + this.startX) * zoomScale), (int)((node1.getY() + this.startY) * zoomScale),
                    (int)((node2.getX() + this.startX) * zoomScale), (int)((node2.getY() + this.startY) * zoomScale));
        }

        // node
        ArrayList<Node> nodes = dataManager.getNodes();
        int offset = dataManager.getNodeSize() / 2;
        for (Node node : nodes) {
            if (node == this.dataManager.getSelectedNode()) {
                this.g2d.setColor(Color.GREEN);
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
            this.g2d.fillOval((int)((node.getX() + this.startX) * zoomScale - offset), (int)((node.getY() + this.startY) * zoomScale - offset),
                    dataManager.getNodeSize(), dataManager.getNodeSize());
        }
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setZoomScale(double zoomScale) {
        this.zoomScale = zoomScale;
    }

    public double getZoomScale() {
        return zoomScale;
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

    public Node detectNode(int x, int y) {

        double realX = transformXToReal(x);
        double realY = transformYToReal(y);
        ArrayList<Node> nodes = dataManager.getNodes();
        int offset = dataManager.getNodeSize() / 2;
        for (Node node : nodes) {

            if (((realX  > node.getX() - offset) && (realX < (node.getX() + offset)))
                    && (realY > node.getY() - offset && realY < node.getY() + offset)) {
                return node;
            }
        }
        return null;
    }

    public Edge detectEdge(int x, int y) {
        double realX = transformXToReal(x);
        double realY = transformYToReal(y);
        ArrayList<Edge> edges = dataManager.getEdges();
        for (Edge edge : edges) {
            if (isPointOnEdge(realX, realY, edge)) {
                return edge;
            }
        }
        return null;
    }

    private boolean isPointOnEdge(double x, double y, Edge edge) {
        int dx = (int) (edge.getNode1().getX() - edge.getNode2().getX());
        int dy = (int) (edge.getNode1().getY() - edge.getNode2().getY());
        double m = (double) dy / dx;
        double c = edge.getNode2().getY() - m * edge.getNode2().getX();

        double calcY = m * x + c;
        return Math.abs(calcY - y) <= dataManager.getEdgeSize() + 5; // + 5 lebo inak je tazke zakliknut
    }

    public double transformXToReal(double localX) {
        return this.startX + localX / zoomScale;
    }

    public double transformYToReal(double localY) {
        return this.startY + localY / zoomScale;
    }
}
