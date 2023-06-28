package GUI;

import Data.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;


public class GUI extends JFrame{
    private final JTextArea textArea;
    private final JXMapViewer mapViewer;
    private final MapPainter mapPainter;

    private DataManager dataManager;
    //private DistancesWindow tableWindow;
    private final double initialLatitude = 19.543304443359375;
    private final double initialLongitude = 49.02075978339726;

    public GUI() throws HeadlessException {
        this.dataManager = new DataManager(4,2);
        //this.tableWindow = new DistancesWindow(this.dataManager);

        // Create Form
        setTitle("Informačný systém dopravného podniku");
        setSize(new Dimension(1600,800));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(128, 128, 128));
        setLocationRelativeTo(null);

        // Create a canvas panel
        JPanel canvasPanel = new JPanel(new BorderLayout());

        // Create a JXMapViewer and MapPainter
        TileFactoryInfo osmInfo = new OSMTileFactoryInfo();
        mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(new DefaultTileFactory(osmInfo));
        mapViewer.setZoom(9);
        mapViewer.setAddressLocation(new GeoPosition(initialLongitude, initialLatitude));
        MouseInputListener mia = new PanMouseInputListener(mapViewer);

        this.mapPainter = new MapPainter(dataManager);
        mapViewer.setOverlayPainter(mapPainter);

        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        canvasPanel.add(mapViewer);

        // Create a panel for displaying text
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(new Color(43, 45, 48));
        this.textArea = new JTextArea(10, 20);
        this.textArea.setEditable(true);
        Font font = this.textArea.getFont();
        Font newFont = font.deriveFont(font.getSize() + 5f); // Increase the font size by 4
        this.textArea.setFont(newFont);
        this.textArea.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(this.textArea);
        textPanel.add(scrollPane, BorderLayout.NORTH);

        // Edit Panel
        JPanel editPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        editPanel.setBackground(Color.GRAY);
        int padding = 10;
        editPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));

        JLabel leftLabel = new JLabel("Lava hodnota");
        JTextField leftTextField = new JTextField();
        JLabel mainLabel = new JLabel("Hlavna hodnota");
        JTextField mainTextField = new JTextField();
        JLabel rightLabel = new JLabel("Prava hodnota");
        JTextField rightTextField = new JTextField();
        editPanel.add(leftLabel);
        editPanel.add(leftTextField);
        editPanel.add(mainLabel);
        editPanel.add(mainTextField);
        editPanel.add(rightLabel);
        editPanel.add(rightTextField);
        JButton saveLengthButton = new JButton("Uložiť");
        editPanel.add(saveLengthButton);

        // Adding edit Panel to textPanel
        textPanel.add(editPanel, BorderLayout.CENTER);

        // Adding spacer to textPanel
        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(500, 250));
        spacerPanel.setBackground(new Color(77, 78, 81));
        textPanel.add(spacerPanel, BorderLayout.SOUTH);

        // Create a main panel and set its layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(canvasPanel, BorderLayout.CENTER);
        mainPanel.add(textPanel, BorderLayout.EAST);

        // Add the main panel to the frame
        getContentPane().add(mainPanel);

        // Display the GUI
        setVisible(true);

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
            System.out.println(mapViewer.getZoom());
        });

        saveLengthButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                try {
                    Number left_number = format.parse(leftTextField.getText());
                    Number main_number = format.parse(mainTextField.getText());
                    Number right_number = format.parse(rightTextField.getText());

                    double left = left_number.doubleValue();
                    double main = main_number.doubleValue();
                    double right = right_number.doubleValue();

                    dataManager.updateEdgeLength(left, main, right);
                    dataManager.unselect();
                    textArea.setText("");
                    repaint();
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Zmeny sa nepodarilo uložiť", "Chyba pri zmene hodnôt" , JOptionPane.ERROR_MESSAGE);;
                }

                leftTextField.setText("");
                mainTextField.setText("");
                rightTextField.setText("");
            }
        });
        // ActionListener for the distance button
        //distancesButton.addActionListener(e -> this.tableWindow.openWindow());

        // ActionListener for the update distances button
        //updateDistancesButton.addActionListener(e -> this.dataManager.updateDistancesMatrix());

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
        this.dataManager.setSelectedEdge(edge);
        getInfo(edge);
        this.mapViewer.repaint();
    }
}