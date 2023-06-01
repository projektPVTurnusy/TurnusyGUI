package GUI;

import Data.DataManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DistancesWindow {
    private DataManager dataManager;

    public DistancesWindow(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void openWindow() {
        JFrame frame = new JFrame("Distance Table");
        double[][] distances = this.dataManager.getDistances();

        if (distances.length > 0) {
            Object[][] data = new Object[distances.length][distances[0].length];
            for (int i = 0; i < distances.length; i++) {
                for (int j = 0; j < distances[i].length; j++) {
                    data[i][j] = distances[i][j];
                }
            }
            JTable table = new JTable(new DefaultTableModel(data, getColumnNames()));
            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane);
        }

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private String[] getColumnNames() {
        int numNodes = dataManager.getDistances().length;
        String[] columnNames = new String[numNodes];
        for (int i = 0; i < numNodes; i++) {
            columnNames[i] = "Node " + (i + 1);
        }
        return columnNames;
    }
}
