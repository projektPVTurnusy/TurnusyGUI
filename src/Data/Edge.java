package Data;

public class Edge implements ObjInfo {

    private int id;
    private Node node1;
    private Node node2;
    private double length;
    private boolean exactLength;
    private String name;

    /*
    *   int id, Node node1, Node node2, double length, boolean exactLength, String name
    * */

    public Edge(int id, Node n1, Node n2, String name) {
        this(id, n1, n2, Math.sqrt((n2.getY() - n1.getY()) * (n2.getY() - n1.getY()) + (n2.getX() - n1.getX()) * (n2.getX() - n1.getX())), true, name);
    }
    public Edge(int id, Node n1, Node n2, double length, String name) {
        this(id, n1, n2, length, false, name);
    }

    public Edge(int id, Node n1, Node n2, double length, boolean exactLength, String name) {
        if (n1 == null || n2 == null) throw new RuntimeException("Cant create edge with null nodes!");
        this.id = id;
        this.node1 = n1;
        n1.addEdge(this);
        this.node2 = n2;
        n2.addEdge(this);
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
            this.node1 = node1;
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
        this.node2 = node2;
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

    public String[] getInfo() {
        String[] info = new String[5];
        info[0] = "  ID: " + this.id + "\n";
        info[1] = "  Name: " + this.name + "\n";
        info[2] = "  Length: " + this.length + "\n";
        info[3] = "  Node1 ID: " + this.node1.getId() + "\n";
        info[4] = "  Node2 ID: " + this.node2.getId() + "\n";
        return info;
    }

    public Node getOtherNode(Node node) {
        if (this.node1 == node) {
            return node2;
        } else {
            return node1;
        }
    }
}
