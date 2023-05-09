/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import Data.*;


public class Canvas extends javax.swing.JComponent {

    private int nodeSize;
    private double edgeSize;
    private double mapSize;
    private double startX = 0;
    private double startY = 0;
    private ArrayList<Edge> edges;
    private ArrayList<Node> nodes;

    
    /**
     * Creates new form Canvas2
     */
    public Canvas() {
        this.nodeSize = 7;
        this.edgeSize = 0.7;
        setPreferredSize(new Dimension(1370,740));
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawRect(0, 0, 50, 50);

        // Draw a blue oval
        g.setColor(Color.BLUE);
        g.drawOval(30, 30, 50, 50);
        /*
        * g.setColor(Color.RED);
        g.drawRect(10, 10, 50, 50);

        // Draw a blue oval
        g.setColor(Color.BLUE);
        g.drawOval(30, 30, 50, 50);
        * */

    }

    public void redraw() {
        // TODO
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
        redraw();
    }

    public void addNode(Node node) {
        this.nodes.add(node);
        redraw();
    }

    public boolean removeEdge(Edge edge) {
        boolean temp = this.edges.remove(edge);
        redraw();
        return temp;
    }

    public boolean removeNode(Node node) {
        boolean temp = this.nodes.remove(node);
        redraw();
        return temp;
    }

    public void selectObj(boolean type, int objID) {
        // TODO
        if (type) {
            // zmenit farbu nodu
        } else {
            // zmenit farbu egde
        }
    }

    public void unSelectObj(boolean type, int objID) {
        // TODO
        if (type) {
            // zmenit farbu nodu
        } else {
            // zmenit farbu egde
        }
    }



}
