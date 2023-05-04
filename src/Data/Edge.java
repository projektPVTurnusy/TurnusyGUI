package Data;

public class Edge {

    private int id;
    private Node node1;
    private Node node2;
    private double length;
    private boolean exactLength; // na posuvanie
    private String name;


    public Edge(int id, Node n1, Node n2, String name) {
        this(id, n1, n2, 0, true, name);
    }

    public Edge(int id, Node n1, Node n2, double length, String name) {
        this(id, n1, n2, length, false, name);
    }

    public Edge(int id, Node n1, Node n2, double length, boolean exactLength, String name) {
        if (n1 == null || n2 == null) throw new RuntimeException("Cant create edge with null nodes!");
        this.id = id;
        this.node1 = n1;
        this.node2 = n2;
        this.length = length;
        this.exactLength = exactLength;
        this.name = name;
        if (this.exactLength) {
            this.length = this.recalculateDistance();
        }
    }

    public int getId() {
        return id;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public double getLength() {
        return length;
    }

    public boolean isExactLength() {
        return exactLength;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNode1(Node node1) {
        if (node1 == null) {
            return;
        }
        this.node1.removeEdge(this);
        this.node1 = node1;
        this.node1.addEdge(this);
        if (this.exactLength) {
            this.length = this.recalculateDistance();
        }
    }

    public void setNode2(Node node2) {
        if (node2 == null) {
            return;
        }
        this.node2.removeEdge(this);
        this.node2 = node2;
        this.node2.addEdge(this);
        if (this.exactLength) {
            this.length = this.recalculateDistance();
        }
    }

    public void setLength(double length) {
        this.length = length;
        this.exactLength = false;
    }

    public void setExactLength(boolean exactLength) {
        this.exactLength = exactLength;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double lengthX = Math.pow(x1 - x2, 2);
        double lengthY = Math.pow(y1 - y2, 2);
        return Math.sqrt(lengthX + lengthY);
    }

    private double recalculateDistance() {
        double lengthX = Math.pow(this.node1.getX() - this.node2.getX(), 2);
        double lengthY = Math.pow(this.node1.getY() - this.node2.getY(), 2);
        return Math.sqrt(lengthX + lengthY);
    }

    public void updateDistance() {
        if (this.exactLength) {
            this.length = this.recalculateDistance();
        }
    }





    /*
    @Override
    public String toString() {
        return "Data.Edge{" + "id=" + id + ", n1=" + node1 + ", n2=" + node2 + ", length=" + length + ", exactLength=" + exactLength + ", allow=" + allow + ", name=" + name + '}';
    }

    public String toSave() {
        return this.id + "\t" + this.node1.getId() + "\t" + this.node2.getId() + "\t" + this.length + "\t" + (this.exactLength ? 1 : 0) + "\t" + (this.allow ? 1 : 0) + "\t" + this.name;
    }
    */

}
