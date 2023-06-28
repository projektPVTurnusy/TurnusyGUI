package Data;

public class Edge implements ObjInfo {
    private int id;
    private Node node1;
    private Node node2;
    private FuzzyNumber length;

    public Edge(int id, Node n1, Node n2, double length)
    {
        if (n1 == null || n2 == null) throw new RuntimeException("Cant create edge with null nodes!");
        this.id = id;
        this.node1 = n1;
        this.node2 = n2;
        this.length = new FuzzyNumber(length, length, length);
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

    public void setLength(double left, double main, double right)
    {
        this.length.setLeft(left);
        this.length.setMain(main);
        this.length.setRight(right);
    }

    public String[] getInfo()
    {
        String[] info = new String[4];
        info[0] = "  ID: " + this.id + "\n";
        info[1] = "  Dĺžka prejazdu: <" + this.length.getLeft() + ", " + this.length.getMain() + ", " + this.length.getRight() + ">\n";
        info[2] = "  Názov zastávky 1: " + this.node1.getName() + "\n";
        info[3] = "  Názov zastávky 2: " + this.node2.getName() + "\n";
        return info;
    }

    public Node getOtherNode(Node node)
    {
        if (this.node1 == node)
        {
            return node2;
        }
        else
        {
            return node1;
        }
    }
}
