/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.awt.*;


public class Canvas extends javax.swing.JPanel {

    private int nodeSize;
    private double edgeSize;
    private double mapSize;

    
    /**
     * Creates new form Canvas2
     */
    public Canvas() {
        initComponents();
        nodeSize = 7;
        edgeSize = 0.7;


    }

    private void initComponents() {
        setBackground(new Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }

    /*
   private void drawMesh(Graphics g, double scale) {
       int countX = (int)(this.getWidth() / (scale * 10.0));
       int countY = (int)(this.getHeight()/ (scale * 10.0));

       g.setColor(Color.BLACK);
       for (int x = 0; x <= countX; ++x) {
           g.drawLine((int)(x * 10 * scale), 0, (int)(x * 10 * scale), this.getHeight());
       }
       for (int y = 0; y <= countY; ++y) {
           g.drawLine(0, (int)(y * 10 * scale), this.getWidth(), (int)(y * 10 * scale));
       }
   }


*/
}
