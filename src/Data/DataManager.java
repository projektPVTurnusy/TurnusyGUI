package Data;

import Utils.Reader;

import java.io.*;
import java.util.*;
public class DataManager {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private Node selectedNode;
    private Edge selectedEdge;
    private final int nodeSize;
    private final double edgeSize;
    private double[][] distances;



    public DataManager(int nodeSize, double edgeSize) {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.selectedNode = null;
        this.selectedEdge = null;
        this.nodeSize = nodeSize;
        this.edgeSize = edgeSize;
        this.distances = new double[0][0];
        loadNodes();
    }

    public double[][] getDistances() {
        return distances;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void addNode(int x, int y) {
        this.nodes.add(new Node(new ArrayList<>(), this.nodes.size(), x, y, "no name", NodeType.UNSPECIFIED ));
    }

    public void addEdge(Node node1, Node node2) {
        this.edges.add(new Edge(this.edges.size(), node1, node2, "no name"));
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public Node getNode(int id) {
        for (Node node : this.nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    public Edge getEdge(int id) {
        for (Edge edge : edges) {
            if (edge.getId() == id) {
                return edge;
            }
        }
        return null;
    }

    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
        for (int i = 0; i < this.edges.size(); ++i) {
            Edge edgeTemp = this.edges.get(i);
            if (edgeTemp.getId() != i) {
                edgeTemp.setId(i);
            }
        }

    }

    public void removeNode(Node node) {
        for (int i = 0; i < node.getEdges().size(); i++) {
            Edge edge = node.getEdges().get(i);
            Node otherNode = edge.getOtherNode(node);
            otherNode.removeEdge(edge);
            this.edges.remove(edge);
        }
        this.nodes.remove(node);
        for (int i = 0; i < this.nodes.size(); ++i) {
            Node nodeTemp = this.nodes.get(i);
            if (nodeTemp.getId() != i) {
                nodeTemp.setId(i);
            }
        }
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

    public Edge getSelectedEdge() {
        return selectedEdge;
    }

    public void setSelectedNode(Node selectedNode) {
        this.selectedNode = selectedNode;
        this.selectedEdge = null;
    }

    public void setSelectedEdge(Edge selectedEdge) {
        this.selectedEdge = selectedEdge;
        this.selectedNode = null;
    }

    public void generate(int count) {
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; ++i) {
            addNode(new Node(new ArrayList<>(), i, random.nextInt(500) + 100, random.nextInt(500) + 100, "FakeNode", NodeType.UNSPECIFIED));
        }
        for (int i = 0; i < count - 1; ++i) {
            addEdge(new Edge(i, getNode(i), getNode(i + 1), "FakeNode"));
        }
        updateDistancesMatrix();
    }

    public void unselect() {
        this.selectedEdge = null;
        this.selectedNode = null;
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public double getEdgeSize() {
        return edgeSize;
    }

    public void save() {
        try {
            BufferedWriter nodeWriter = new BufferedWriter(new FileWriter("nodes.dat"));
            for (Node node : nodes) {
                String nodeData = node.getId() + "," + node.getX() + "," + node.getY() + ","
                        + node.getName() + "," + node.getType() + "," + node.getValue();
                nodeWriter.write(nodeData);
                nodeWriter.newLine();
            }
            nodeWriter.close();

            BufferedWriter edgeWriter = new BufferedWriter(new FileWriter("edges.dat"));
            for (Edge edge : edges) {
                String edgeData = edge.getId() + "," + edge.getNode1().getId() + "," + edge.getNode2().getId() + ","
                        + edge.getLength() + "," + edge.isExactLength() + "," + edge.getName();
                edgeWriter.write(edgeData);
                edgeWriter.newLine();
            }
            edgeWriter.close();

            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        try {
            BufferedReader nodeReader = new BufferedReader(new FileReader("nodes.dat"));
            String nodeData;
            while ((nodeData = nodeReader.readLine()) != null) {
                String[] nodeTokens = nodeData.split(",");
                int id = Integer.parseInt(nodeTokens[0]);
                double x = Double.parseDouble(nodeTokens[1]);
                double y = Double.parseDouble(nodeTokens[2]);
                String name = nodeTokens[3];
                NodeType type = NodeType.valueOf(nodeTokens[4]);
                double value = Double.parseDouble(nodeTokens[5]);
                Node node = new Node(id, x, y, name, type, value);
                this.nodes.add(node);
            }
            nodeReader.close();

            BufferedReader edgeReader = new BufferedReader(new FileReader("edges.dat"));
            String edgeData;
            while ((edgeData = edgeReader.readLine()) != null) {
                String[] edgeTokens = edgeData.split(",");
                int id = Integer.parseInt(edgeTokens[0]);
                int node1Id = Integer.parseInt(edgeTokens[1]);
                int node2Id = Integer.parseInt(edgeTokens[2]);
                double length = Double.parseDouble(edgeTokens[3]);
                boolean exactLength = Boolean.parseBoolean(edgeTokens[4]);
                String name = edgeTokens[5];
                Node node1 = findNodeById(this.nodes, node1Id);
                Node node2 = findNodeById(this.nodes, node2Id);
                Edge edge = new Edge(id, node1, node2, length, exactLength, name);
                this.edges.add(edge);
            }
            edgeReader.close();

            System.out.println("Data loaded successfully.");
            updateDistancesMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Node findNodeById(ArrayList<Node> nodes, int nodeId) {
        for (Node node : nodes) {
            if (node.getId() == nodeId) {
                return node;
            }
        }
        return null;
    }

    public boolean checkCoherency() {
        if (this.nodes.size() == 0) {
            return false;
        }
        Set<Node> visitedNodes = new HashSet<>();
        depthTraversal(this.nodes.get(0), visitedNodes);
        return visitedNodes.size() == this.nodes.size();
    }

    private void depthTraversal(Node node, Set<Node> visitedNodes) {
        visitedNodes.add(node);
        for (Edge edge : node.getEdges()) {
            Node adjacentNode = edge.getOtherNode(node);
            if (!visitedNodes.contains(adjacentNode)) {
                depthTraversal(adjacentNode, visitedNodes);
            }
        }
    }

    public void updateDistancesMatrix() {
        int n = nodes.size();
        double[][] distances = new double[n][n];
        for (double[] row : distances) {
            Arrays.fill(row, Double.POSITIVE_INFINITY);
        }
        for (int i = 0; i < n; i++) {
            distances[i][i] = 0.0;
        }


        for (int i = 0; i < n; i++) {
            Node sourceNode = nodes.get(i);
            calcDistancesFromNode(sourceNode, distances[i]);
        }

        this.distances = distances;
    }

    private void calcDistancesFromNode(Node node, double[] distances) {
        boolean[] visited = new boolean[nodes.size()];
        distances[node.getId()] = 0.0;

        for (int count = 0; count < nodes.size() - 1; count++) {
            int closestNode = closestNode(distances, visited);
            visited[closestNode] = true;
            Node currentNode = nodes.get(closestNode);
            ArrayList<Edge> edges = currentNode.getEdges();

            for (Edge edge : edges) {
                Node otherNode = edge.getOtherNode(currentNode);
                if (!visited[otherNode.getId()] && distances[closestNode] + edge.getLength() < distances[otherNode.getId()]) {
                    distances[otherNode.getId()] = distances[closestNode] + edge.getLength();
                }
            }
        }
    }

    private int closestNode(double[] distances, boolean[] visited) {
        double minDistance = Double.POSITIVE_INFINITY;
        int nodeIndex = -1;
        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] <= minDistance) {
                minDistance = distances[i];
                nodeIndex = i;
            }
        }
        return nodeIndex;
    }

    public Node getWarehouse() {
        for (Node node : this.nodes) {
            if (node.getType() == NodeType.WAREHOUSE_SLOT) {
                return node;
            }
        }
        return null;
    }

    private void loadNodes() {
        try {
            this.nodes = Reader.readNodesFromExcelFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
