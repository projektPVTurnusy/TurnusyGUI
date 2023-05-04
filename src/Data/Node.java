package Data;

import java.util.ArrayList;

public class Node {

    private ArrayList<Edge> edges;
    private int id;
    private double x;
    private double y;
    private String name;

    public Node(ArrayList<Edge> edges, int id, double x, double y, String name) {
        this.edges = edges;
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
    }

    public boolean containsEdge(Edge edge) {
        for (Edge temp : this.edges) {
            if (temp == edge) {
                return true;
            }
        }
        return false;
    }

    public boolean connectedNode(Node node) {
        for (Edge temp : this.edges) {
            if (temp.getNode1() == node || temp.getNode2() == node) {
                return true;
            }
        }
        return false;
    }


}
