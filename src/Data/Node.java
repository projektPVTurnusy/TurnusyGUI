package Data;

import java.util.ArrayList;

public class Node implements ObjInfo {

    private ArrayList<Edge> edges;
    private int id;
    private double x;
    private double y;
    private String name;

    public Node(int id, double x, double y, String name)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public String[] getInfo() {
        String[] info = new String[6 + edges.size()];
        //String[] info = new String[4];
        info[0] = "  ID zastávky: " + this.id + "\n";
        info[1] = "  Názov zastávky: " + this.name + "\n";
        info[2] = "  Zemepisná šírka: " + this.x + "\n";
        info[3] = "  Zemepisná dĺžka: " + this.y + "\n";

        for (int i = 0; i < edges.size(); ++i)
        {
            Edge edge = edges.get(i);
            info[i + 6] = " ID hrany: " + edge.getId() + ", názov druhej zastávky: " + edge.getOtherNode(this).getName()  + "\n";
        }

        return info;
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

    public boolean addEdge(Edge newEdge)
    {
        for (Edge edge : edges)
        {
            if((edge.getNode1().getId() == newEdge.getNode1().getId() && edge.getNode2().getId() == newEdge.getNode2().getId())
            || (edge.getNode1().getId() == newEdge.getNode2().getId() && edge.getNode2().getId() == newEdge.getNode1().getId()))
                return false;
        }
        this.edges.add(newEdge);
        return true;
    }
}
