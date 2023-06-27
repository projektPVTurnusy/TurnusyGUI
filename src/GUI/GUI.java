package GUI;

import Data.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;


public class GUI extends JFrame{
    private Canvas canvas;
    private final JTextArea textArea;
    private final JTextArea coherencyTextArea;
    private final JButton deleteButton;
    private DataManager dataManager;
    private boolean addingNode;
    private boolean addingEdge;
    private DistancesWindow tableWindow;
    private double initialX;
    private double initialY;


    public GUI() throws HeadlessException {

        // Create Form
        setTitle("Drawing system");
        setMinimumSize(new Dimension(1600,800));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(128, 128, 128));


        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(43, 45, 48));
        JButton buttonAddE = new JButton("Add Edge");
        JButton buttonAddN = new JButton("Add Node");
        JButton buttonSave = new JButton("Save");
        JButton buttonLoad = new JButton("Load");
        JButton buttonGenerate = new JButton("Generate");
        buttonPanel.add(buttonAddE);
        buttonPanel.add(buttonAddN);
        buttonPanel.add(buttonSave);
        buttonPanel.add(buttonLoad);
        buttonPanel.add(buttonGenerate);

        // Create a canvas panel
        JPanel canvasPanel = new JPanel();
        canvasPanel.setLayout(new OverlayLayout(canvasPanel));
        this.canvas = new Canvas();
        this.canvas.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.canvas.setAlignmentY(Component.CENTER_ALIGNMENT);
        canvasPanel.add(this.canvas, BorderLayout.CENTER);
        this.canvas.setLayout(null);
        // Create a JXMapViewer component

        TileFactoryInfo osmInfo = new OSMTileFactoryInfo();
        final JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(new DefaultTileFactory(osmInfo));
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(new GeoPosition(49.11, 18.68));
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        canvasPanel.add(mapViewer);

        //


        // Create a panel for displaying text
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(new Color(43, 45, 48));
        this.textArea = new JTextArea(10, 20);
        this.textArea.setEditable(true);
        Font font = this.textArea.getFont();
        Font newFont = font.deriveFont(font.getSize() + 5f); // Increase the font size by 4
        this.textArea.setFont(newFont);
        JScrollPane scrollPane = new JScrollPane(this.textArea);
        textPanel.add(scrollPane, BorderLayout.NORTH);

        // Edit Panel
        JPanel editPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        editPanel.setBackground(new Color(77, 78, 81));
        int padding = 10;
        editPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));


        // First row: Name
        JTextArea nameTextArea = new JTextArea("new name");
        JButton nameButton = new JButton("Change");
        editPanel.add(nameTextArea);
        editPanel.add(nameButton);

        // Second row: Value
        JTextArea valueTextArea = new JTextArea("new value");
        JButton valueButton = new JButton("Change");
        editPanel.add(valueTextArea);
        editPanel.add(valueButton);

        // Third row: ComboBox
        String[] options = {"UNSPECIFIED",
                "CUSTOMER",
                "WAREHOUSE_SLOT",
                "SOURCE"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        JButton comboBoxButton = new JButton("Change");
        editPanel.add(comboBox);
        editPanel.add(comboBoxButton);

        // Fourth row: Check coherency button
        this.coherencyTextArea = new JTextArea("no");
        this.coherencyTextArea.setBackground(Color.red);
        Font coherencyFont = this.coherencyTextArea.getFont();
        Font coherencyNewFont = font.deriveFont(coherencyFont.getSize() + 5f); // Increase the font size by 4
        this.coherencyTextArea.setFont(coherencyNewFont);
        this.coherencyTextArea.setEditable(false);
        JButton coherencyButton = new JButton("Check coherency");
        editPanel.add(this.coherencyTextArea);
        editPanel.add(coherencyButton);

        // Fifth row: Show table with distances and update distances buttons
        JButton distancesButton = new JButton("Shortest distances");
        JButton updateDistancesButton = new JButton("Calculate distances");
        editPanel.add(distancesButton);
        editPanel.add(updateDistancesButton);

        // Sixth row: Delete button
        this.deleteButton = new JButton("Delete");
        editPanel.add(this.deleteButton);
        this.deleteButton.setEnabled(false);

        // Adding edit Panel to textPanel
        textPanel.add(editPanel, BorderLayout.CENTER);

        // Adding spacer to textPanel
        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(500, 250));
        spacerPanel.setBackground(new Color(77, 78, 81));
        textPanel.add(spacerPanel, BorderLayout.SOUTH);

        // Create a main panel and set its layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(canvasPanel, BorderLayout.CENTER);
        mainPanel.add(textPanel, BorderLayout.EAST);

        // Add the main panel to the frame
        getContentPane().add(mainPanel);

        // Display the GUI
        setVisible(true);

        // Initialize basic attributes
        initialize();



        // MouseListener for clicking
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                /*
                System.out.println(String.format("mouse x: %s, y: %s ",  e.getX(), e.getY()));
                System.out.println(String.format("x: %s, y: %s ",  canvas.getWidth(), canvas.getHeight()));
                */
                if (SwingUtilities.isRightMouseButton(e)) {
                    unselect();
                    changeCursor();
                } else {
                    cursorSelectObj(e);
                }
            }
        });

        // MouseListener for camera
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
                dataManager.setStartingX(canvas.getStartX());
                dataManager.setStartingY(canvas.getStartY());
            }
        });

        // MouseListener for camera
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double changeX = (e.getX() - initialX) / canvas.getZoomScale();
                double changeY = (e.getY() - initialY) / canvas.getZoomScale();
                double newX = dataManager.getStartingX() + changeX;
                double newY = dataManager.getStartingY() + changeY;
                canvas.setStartX(newX);
                canvas.setStartY(newY);
                canvas.redraw();
            }
        });

        canvas.addMouseWheelListener(e -> {
            int rotation = e.getWheelRotation();
            if (rotation < 0) {
                canvas.setZoomScale(canvas.getZoomScale() + 0.1);
            } else {
                canvas.setZoomScale(canvas.getZoomScale() - 0.1);
                if (canvas.getZoomScale() < 0.1) {
                    canvas.setZoomScale(0.1);
                }
            }
            canvas.redraw();
        });

        // ButtonListener for adding Edge button
        buttonAddE.addActionListener(e -> {
            //System.out.println("add E");
            this.addingEdge = true;
            this.addingNode = false;
            changeCursor();
        });

        // ButtonListener for adding Node button
        buttonAddN.addActionListener(e -> {
            //System.out.println("add N");
            this.addingNode = true;
            this.addingEdge = false;
            changeCursor();
        });

        // ButtonListener for saving button
        buttonSave.addActionListener(e -> {
            //System.out.println("save");
            this.dataManager.save();
        });

        // ButtonListener for loading button
        buttonLoad.addActionListener(e -> {
            //System.out.println("load");
            this.dataManager.load();
            this.canvas.redraw();
            unselect();
            checkCoherency();
        });

        // ButtonListener for generating button
        buttonGenerate.addActionListener(e -> {
            //System.out.println("save");
            this.dataManager.generate(10);
            getInfo(null);
            unselect();
            this.canvas.redraw();
        });

        // ActionListener for the Name button
        nameButton.addActionListener(e -> {
            String newName = nameTextArea.getText();
            Node node = this.dataManager.getSelectedNode();
            Edge edge = this.dataManager.getSelectedEdge();

            if (node != null) {
                node.setName(newName);
                getInfo(node);
            } else if (edge != null) {
                edge.setName(newName);
                getInfo(edge);
            }
            nameTextArea.setText("new Name");
            //System.out.println("New Name: " + newName);
        });

        // ActionListener for the Value button
        valueButton.addActionListener(e -> {
            double newValue = Double.parseDouble(valueTextArea.getText());
            Node node = this.dataManager.getSelectedNode();
            Edge edge = this.dataManager.getSelectedEdge();

            if (node != null) {
                node.setValue(newValue);
                getInfo(node);
            } else if (edge != null) {
                edge.setLength(newValue);
                getInfo(edge);
            }
            valueTextArea.setText("new value");
            //System.out.println("New Value: " + newValue);
        });

        // ActionListener for the distance button
        distancesButton.addActionListener(e -> this.tableWindow.openWindow());

        // ActionListener for the update distances button
        updateDistancesButton.addActionListener(e -> this.dataManager.updateDistancesMatrix());

        // ActionListener for the ComboBox button
        comboBoxButton.addActionListener(e -> {
            String selectedOption = (String) comboBox.getSelectedItem();

            if (this.dataManager.getSelectedNode() != null) {
                assert selectedOption != null;
                NodeType nodeType = getEnum(selectedOption);
                this.dataManager.getSelectedNode().setType(nodeType);
            }
            //System.out.println("Selected Option: " + selectedOption);
        });

        // ActionListener for the deleting button
        deleteButton.addActionListener(e -> {
            if (this.dataManager.getSelectedNode() != null) {
                this.dataManager.removeNode(this.dataManager.getSelectedNode());
                System.out.println("Deleted Node");
            } else if (this.dataManager.getSelectedEdge() != null) {
                this.dataManager.removeEdge(this.dataManager.getSelectedEdge());
                System.out.println("Deleted Edge");
            }
            checkCoherency();
            unselect();
        });

        // ActionListener for the Name button
        coherencyButton.addActionListener(e -> checkCoherency());
    }

    private void initialize() {
        this.initialX = 19.1591;
        this.initialY = 48.7351;
        this.dataManager = new DataManager(30,4, initialX, initialY);
        this.tableWindow = new DistancesWindow(this.dataManager);

        this.canvas.setDataManager(dataManager);
        this.addingNode = false;
        this.addingEdge = false;
        this.canvas.setZoomScale(150);

    }

    private void cursorSelectObj(MouseEvent e) {
        // adding
        if (addingEdge || addingNode) {
            if (addingNode) {
                double realX = canvas.transformXToReal(e.getX());
                double realY = canvas.transformYToReal(e.getY());
                this.dataManager.addNode(realX, realY);
                this.canvas.redraw();
                checkCoherency();
                return;
            } else {
                Node node = this.canvas.detectNode(e.getX(), e.getY());
                if (node != null) {
                    if (dataManager.getSelectedNode() == null) {
                        selectNode(node);
                    } else {
                        this.dataManager.addEdge(dataManager.getSelectedNode(), node);
                        unselect();
                        checkCoherency();
                    }
                    return;
                }

            }
        }

        // detect nodes
        Node node = canvas.detectNode(e.getX(), e.getY());
        //System.out.println("real stX: " + canvas.getStartX() + " stY: " + canvas.getStartY());
        if (node != null) {
            selectNode(node);
            return;
        }

        // detect edge
        Edge edge = canvas.detectEdge(e.getX(), e.getY());
        if (edge != null) {
            selectEdge(edge);
            return;
        }

        unselect();
    }

    private void changeCursor() {
        if (!this.addingNode && !addingEdge) {
            setCursor(Cursor.getDefaultCursor());
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
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

    private void createUIComponents() {
        this.canvas = new Canvas();
    }

    private void checkCoherency() {
        if (dataManager.checkCoherency()) {
            coherencyTextArea.setText("Coherent");
            coherencyTextArea.setBackground(Color.GREEN);
        } else {
            coherencyTextArea.setText("Uncoherent");
            coherencyTextArea.setBackground(Color.RED);
        }
    }



    private void unselect() {
        this.dataManager.unselect();
        getInfo(null);
        this.canvas.redraw();
        this.addingNode = false;
        this.addingEdge = false;
        changeCursor();
        deleteButton.setEnabled(false);
    }

    private void selectNode(Node node) {
        this.dataManager.setSelectedNode(node);
        getInfo(node);
        this.deleteButton.setEnabled(true);
        this.canvas.redraw();
    }

    private void selectEdge(Edge edge) {
        this.dataManager.setSelectedEdge(edge);
        getInfo(edge);
        this.deleteButton.setEnabled(true);
        this.canvas.redraw();
    }

    private NodeType getEnum(String input) {
        NodeType nodeType;

        switch (input) {
            case "SOURCE":
                nodeType = NodeType.SOURCE;
                break;
            case "CUSTOMER":
                nodeType = NodeType.CUSTOMER;
                break;
            case "WAREHOUSE_SLOT":
                nodeType = NodeType.WAREHOUSE_SLOT;
                break;
            default:
                nodeType = NodeType.UNSPECIFIED;
                break;
        }
        return nodeType;
    }
}