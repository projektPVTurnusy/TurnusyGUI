package Data;

import Util.Reader;

import java.io.*;
import java.util.*;
public class DataManager {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private Node selectedNode;
    private Edge selectedEdge;
    private int nodeSize;
    private double edgeSize;
    private double[][] distances;

    public DataManager(int nodeSize, double edgeSize) {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.selectedNode = null;
        this.selectedEdge = null;
        this.nodeSize = nodeSize;
        this.edgeSize = edgeSize;
        this.distances = new double[0][0];
        loadData();

        for(Node node: nodes)
            if(node.getX() == 0)
                System.out.println(node.getName());
    }

    public double[][] getDistances() {
        return distances;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
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

    public void updateSizes(int nodeSize, int edgeSize)
    {
        this.nodeSize = nodeSize;
        this.edgeSize = edgeSize;
    }

    private void loadData() {
        try {
            Reader reader = new Reader(this);
            this.nodes = reader.readNodesFromExcelFile();
            this.edges = reader.readEdgesFromExcelFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

    public void updateEdgeLength(double left, double main, double right)
    {
        if(this.selectedEdge != null)
        {
            this.selectedEdge.setLength(left, main, right);
            unselect();
        }
    }

    /*
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
     */
}
