package Data;

import java.util.ArrayList;

public class Node implements ObjInfo {

    private ArrayList<Edge> edges;
    private int id;
    private double x;
    private double y;
    private String name;
    private NodeType type;
    private double value;
    /*
    *  ArrayList<Edge> edges, int id, final double x, final double y, String name, NodeType type, double value
    * */


    public Node(ArrayList<Edge> edges, int id, double x, double y, String name, NodeType nodeType) {
        this.edges = edges;
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
        this.type = nodeType;
        this.value = 0;
    }

    public Node(int id, double x, double y, String name, NodeType type, double value) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
        this.type = type;
        this.value = value;
        this.edges = new ArrayList<>();
    }

      public String[] getInfo() {
        String[] info = new String[6 + edges.size()];
        info[0] = "  ID: " + this.id + "\n";
        info[1] = "  Name: " + this.name + "\n";
        switch (this.type) {
            case CUSTOMER:
                info[2] = "  Demand: " + this.value + "\n";
                break;
            case WAREHOUSE_SLOT:
                info[2] = "  Capacity: " + this.value + "\n";
                break;
            default:
                info[2] = "  Value: " + this.value + "\n";
                break;
        }
        info[3] = "  X: " + this.x + "\n";
        info[4] = "  Y: " + this.y + "\n";
        info[5] = "  Type: " + this.type + "\n";
        for (int i = 0; i < edges.size(); ++i) {
            Edge edge = edges.get(i);
            info[i + 6] = "  Edge ID: " + edge.getId() + ", to node ID: " + edge.getOtherNode(this).getId()  + "\n";
        }
        return info;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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
