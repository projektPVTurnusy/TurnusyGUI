package Data;

import java.util.HashMap;

public class DataManager {
    private HashMap<Integer, Node> nodes;
    private HashMap<Integer, Edge> edges;

    private Node selectedNode;
    private Edge selectedEdge;

    public DataManager(HashMap<Integer, Node> nodes, HashMap<Integer, Edge> edges, Node selectedNode, Edge selectedEdge) {
        this.nodes = nodes;
        this.edges = edges;
        this.selectedNode = null;
        this.selectedEdge = null;
    }

    public void addNode(Node node) {
        this.nodes.put(node.getId(), node);
    }

    public void addEdge(Edge edge) {
        this.edges.put(edge.getId(), edge);
    }

    public void getNode(Node node) {
        this.nodes.put(node.getId(), node);
    }

    public void getEdge(Edge edge) {
        this.edges.put(edge.getId(), edge);
    }

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public HashMap<Integer, Edge> getEdges() {
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
}
