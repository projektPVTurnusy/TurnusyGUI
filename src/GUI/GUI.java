package GUI;

import Data.DataManager;
import Data.Edge;
import Data.Node;
import Data.ObjInfo;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class GUI {
    private JXMapViewer mapViewer;
    private MapPainter mapPainter;

    private DataManager dataManager;
    //private DistancesWindow tableWindow;
    private final double initialLatitude = 19.543304443359375;
    private final double initialLongitude = 49.02075978339726;
    private JFrame frame;
    private javax.swing.JPanel drawingPanel;
    private javax.swing.JLabel editEdgeLabel;
    private javax.swing.JButton editEdgeButton;
    private javax.swing.JPanel editEdgePanel;
    private javax.swing.JLabel leftValueLabel;
    private javax.swing.JTextField leftValueTextField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel mainValueLabel;
    private javax.swing.JTextField mainValueTextField;
    private javax.swing.JLabel rightValueLabel;
    private javax.swing.JTextField rightValueTextField;
    private javax.swing.JTextArea textArea;
    private javax.swing.JScrollPane textScrollPane;

    public GUI() {
        this.initialize();
    }

    private void initialize()
    {
        this.dataManager = new DataManager(4,2);

        frame = new JFrame();
        mainPanel = new javax.swing.JPanel();
        drawingPanel = new javax.swing.JPanel();
        editEdgePanel = new javax.swing.JPanel();
        editEdgeLabel = new javax.swing.JLabel();
        mainValueLabel = new javax.swing.JLabel();
        mainValueTextField = new javax.swing.JTextField();
        rightValueLabel = new javax.swing.JLabel();
        rightValueTextField = new javax.swing.JTextField();
        leftValueLabel = new javax.swing.JLabel();
        leftValueTextField = new javax.swing.JTextField();
        editEdgeButton = new javax.swing.JButton();
        textScrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();

        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Informačný systém dopravného podniku");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        mainPanel.setPreferredSize(new java.awt.Dimension(1275, 925));

        drawingPanel.setBackground(new java.awt.Color(255, 255, 255));

        //////////////
        // Create a JXMapViewer and MapPainter
        TileFactoryInfo osmInfo = new OSMTileFactoryInfo();
        TileFactoryInfo veInfoMap = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        TileFactoryInfo veInfoHybrid = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        final ArrayList<TileFactory> factories = new ArrayList<>();
        factories.add(new DefaultTileFactory(osmInfo));
        factories.add(new DefaultTileFactory(veInfoMap));
        factories.add(new DefaultTileFactory(veInfoHybrid));

        mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(factories.get(0));
        mapViewer.setZoom(9);
        mapViewer.setAddressLocation(new GeoPosition(initialLongitude, initialLatitude));
        MouseInputListener mia = new PanMouseInputListener(mapViewer);

        this.mapPainter = new MapPainter(dataManager);
        mapViewer.setOverlayPainter(mapPainter);

        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        drawingPanel.add(mapViewer);
        drawingPanel.repaint();

        String[] tfLabels = new String[factories.size()];
        tfLabels[0] = "OpenStreetMap";
        tfLabels[1] = "Virtual Earth - Cestná mapa";
        tfLabels[2] = "Virtual Earth - Hybridná mapa";

        final JComboBox combo = new JComboBox(tfLabels);
        combo.addItemListener(e -> {
            TileFactory factory = factories.get(combo.getSelectedIndex());
            mapViewer.setTileFactory(factory);
        });
        //////////////

        javax.swing.GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
                drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 900, Short.MAX_VALUE)
                        .addComponent(mapViewer)
        );
        drawingPanelLayout.setVerticalGroup(
                drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 900, Short.MAX_VALUE)
                        .addComponent(mapViewer)
        );

        editEdgePanel.setBackground(new java.awt.Color(204, 204, 204));

        editEdgeLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        editEdgeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editEdgeLabel.setText("Editácia vybratej hrany");

        mainValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mainValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainValueLabel.setText("Hlavná hodnota");

        mainValueTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        rightValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rightValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rightValueLabel.setText("Pravá hodnota");

        rightValueTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        leftValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        leftValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        leftValueLabel.setText("Ľavá hodnota");

        leftValueTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        editEdgeButton.setText("Uložiť");
        editEdgeButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout editEdgePanelLayout = new javax.swing.GroupLayout(editEdgePanel);
        editEdgePanel.setLayout(editEdgePanelLayout);
        editEdgePanelLayout.setHorizontalGroup(
                editEdgePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editEdgePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(editEdgePanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(editEdgeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(editEdgePanelLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(editEdgePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(editEdgePanelLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(leftValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(10, 10, 10)
                                                                .addComponent(leftValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(editEdgePanelLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addGroup(editEdgePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(mainValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(10, 10, 10)
                                                                        .addComponent(rightValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGap(10, 10, 10)
                                                                .addGroup(editEdgePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(mainValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(10, 10, 10)
                                                                        .addComponent(rightValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(editEdgeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))))
                                                .addGap(10, 10, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        editEdgePanelLayout.setVerticalGroup(
                editEdgePanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(editEdgePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(editEdgeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(editEdgePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(leftValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(leftValueLabel))
                                .addGap(9, 9, 9)
                                .addGroup(editEdgePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(mainValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mainValueLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(editEdgePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rightValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rightValueLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editEdgeButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        textArea.setColumns(20);
        textArea.setRows(5);

        Font font = this.textArea.getFont();
        Font newFont = font.deriveFont(font.getSize() + 4f); // Increase the font size by 4
        this.textArea.setFont(newFont);

        textArea.setFocusable(false);
        textScrollPane.setViewportView(textArea);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(drawingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(editEdgePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(textScrollPane)
                                        .addComponent(combo))
                                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, 10)
                                                .addComponent(textScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(editEdgePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(drawingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        editEdgePanel.setVisible(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cursorSelectObj(e);
            }
        });

        mapViewer.addMouseWheelListener(e -> {
            switch(mapViewer.getZoom())
            {
                case 10:
                case 9:
                case 8:
                case 7:
                case 6: {dataManager.updateSizes(4,2);break;}
                case 5:
                case 4: {dataManager.updateSizes(6,2);break;}
                case 3:
                case 2: {dataManager.updateSizes(8,3);break;}
                case 1:
                case 0: {dataManager.updateSizes(10,3);break;}
            }
        });

        editEdgeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                try {
                    Number left_number = format.parse(leftValueTextField.getText());
                    Number main_number = format.parse(mainValueTextField.getText());
                    Number right_number = format.parse(rightValueTextField.getText());

                    double left = left_number.doubleValue();
                    double main = main_number.doubleValue();
                    double right = right_number.doubleValue();

                    dataManager.updateEdgeLength(left, main, right);
                    frame.repaint();
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Zmeny sa nepodarilo uložiť", "Chyba pri zmene hodnôt" , JOptionPane.ERROR_MESSAGE);
                }
                Edge edge = dataManager.getSelectedEdge();
                getInfo(edge);
                leftValueTextField.setText(format.format(edge.getLeft()));
                mainValueTextField.setText(format.format(edge.getMain()));
                rightValueTextField.setText(format.format(edge.getRight()));
            }
        });
    }

    private void cursorSelectObj(MouseEvent e) {
        // detect nodes
        GeoPosition clickedPosition = mapViewer.convertPointToGeoPosition(new Point(e.getX(), e.getY()));
        Node node = mapPainter.detectNode(mapViewer, clickedPosition);
        if (node != null)
        {
            selectNode(node);
            return;
        }

        // detect edge
        Edge edge = mapPainter.detectEdge(mapViewer, clickedPosition);
        if (edge != null)
        {
            selectEdge(edge);
            return;
        }

        unselect();
    }

    public void getInfo(ObjInfo item) {
        this.textArea.setText("");
        if (item == null) {
            return;
        }
        String[] strings = item.getInfo();
        for (String string : strings) {
            this.textArea.append(string);
        }
    }

    private void unselect() {
        editEdgePanel.setVisible(false);
        this.dataManager.unselect();
        getInfo(null);
        this.mapViewer.repaint();
    }

    private void selectNode(Node node) {
        this.dataManager.setSelectedNode(node);
        getInfo(node);
        this.mapViewer.repaint();
    }

    private void selectEdge(Edge edge) {
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

        this.dataManager.setSelectedEdge(edge);
        getInfo(edge);
        leftValueTextField.setText(format.format(edge.getLeft()));
        mainValueTextField.setText(format.format(edge.getMain()));
        rightValueTextField.setText(format.format(edge.getRight()));
        editEdgePanel.setVisible(true);
        this.mapViewer.repaint();
    }
}