package GUI;

import Data.DataManager;
import Data.Edge;
import Data.Node;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MapPainter implements org.jxmapviewer.painter.Painter<JXMapViewer> {

    private final DataManager dataManager;

    public MapPainter(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer mapViewer, int width, int height)
    {
        //edges
        ArrayList<Edge> edges = dataManager.getEdges();
        g.setStroke(new BasicStroke((float) dataManager.getEdgeSize()));
        for (Edge edge : edges)
        {
            if (edge == this.dataManager.getSelectedEdge())
            {
                g.setColor(Color.GREEN);
            }
            else
            {
                g.setColor(Color.LIGHT_GRAY);
            }

            Node node1 = edge.getNode1();
            Node node2 = edge.getNode2();

            Point2D point1 = mapViewer.getTileFactory().geoToPixel(new GeoPosition(node1.getY(), node1.getX()), mapViewer.getZoom());
            double x1 = point1.getX() - mapViewer.getViewportBounds().getX();
            double y1 = point1.getY() - mapViewer.getViewportBounds().getY();

            Point2D point2 = mapViewer.getTileFactory().geoToPixel(new GeoPosition(node2.getY(), node2.getX()), mapViewer.getZoom());
            double x2 = point2.getX() - mapViewer.getViewportBounds().getX();
            double y2 = point2.getY() - mapViewer.getViewportBounds().getY();

            Line2D line = new Line2D.Double(x1, y1, x2, y2);
            g.draw(line);
        }

        // nodes

        ArrayList<Node> nodes = dataManager.getNodes();
        for (Node node : nodes)
        {
            if (node == this.dataManager.getSelectedNode())
            {
                g.setColor(Color.GREEN);
            }
            else
            {
                g.setColor(Color.BLACK);
            }
            if(node.getX() != 0)
            {
                Point2D point = mapViewer.getTileFactory().geoToPixel(new GeoPosition(node.getY(), node.getX()), mapViewer.getZoom());
                double x = point.getX() - mapViewer.getViewportBounds().getX();
                double y = point.getY() - mapViewer.getViewportBounds().getY();

                Ellipse2D circle = new Ellipse2D.Double(
                        x - (double) dataManager.getNodeSize() / 2,
                        y - (double) dataManager.getNodeSize() / 2,
                        dataManager.getNodeSize(), dataManager.getNodeSize());
                g.fill(circle);
            }
        }
        //license
        String license = mapViewer.getTileFactory().getInfo().getAttribution();
        g.setColor(Color.WHITE);
        Rectangle2D rect = new Rectangle2D.Double(mapViewer.getHeight() - license.length()*6, mapViewer.getWidth()-20,210,20);
        g.fill(rect);
        g.setColor(Color.BLACK);
        g.drawString(license, mapViewer.getHeight() - license.length()*6+1, mapViewer.getWidth()-6);
        //g.drawString("© Openstreetmap (osm.org/copyright)", mapViewer.getHeight()-208, mapViewer.getWidth()-6);
    }

    public Node detectNode(JXMapViewer mapViewer, GeoPosition position)
    {
        ArrayList<Node> nodes = dataManager.getNodes();
        Point2D positionPoint = mapViewer.getTileFactory().geoToPixel(position, mapViewer.getZoom());
        for (Node node: nodes)
        {
            GeoPosition positionOfNode = new GeoPosition(node.getY(), node.getX());
            Point2D positionOfNodePoint = mapViewer.getTileFactory().geoToPixel(positionOfNode, mapViewer.getZoom());

            Rectangle2D clickArea = new Rectangle2D.Double(
                    positionOfNodePoint.getX() - (double) dataManager.getNodeSize() / 2,
                    positionOfNodePoint.getY() - (double) dataManager.getNodeSize() / 2,
                    dataManager.getNodeSize(),
                    dataManager.getNodeSize());

            if(clickArea.contains(positionPoint))
            {
                return node;
            }
        }
        return null;
    }

    public Edge detectEdge(JXMapViewer mapViewer, GeoPosition position)
    {
        ArrayList<Edge> edges = dataManager.getEdges();
        Point2D positionPoint = mapViewer.getTileFactory().geoToPixel(position, mapViewer.getZoom());
        for (Edge edge: edges)
        {
            Point2D point1 = mapViewer.getTileFactory().geoToPixel(new GeoPosition(edge.getNode1().getY(), edge.getNode1().getX()), mapViewer.getZoom());
            Point2D point2 = mapViewer.getTileFactory().geoToPixel(new GeoPosition(edge.getNode2().getY(), edge.getNode2().getX()), mapViewer.getZoom());

            double distance = Line2D.ptSegDist(
                    point1.getX(),point1.getY(),
                    point2.getX(),point2.getY(),
                    positionPoint.getX(), positionPoint.getY());
            if(distance < dataManager.getEdgeSize() / 2)
            {
                return edge;
            }
        }
        return null;
    }
}
