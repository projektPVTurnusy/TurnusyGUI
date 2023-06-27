package Main;

import Data.DataManager;
import GUI.*;

import java.io.IOException;

public class Main {
    private DataManager dataManager;


    public static void main(String[] args) throws IOException {
        GUI windowGUI = new GUI();
        /*
        DataManager dataManager = new DataManager(1, 1);
        Reader reader = new Reader(dataManager);
        dataManager.setNodes(reader.readNodesFromExcelFile());
        // for (Node node : dataManager.getNodes()) {
        //     System.out.println(node.getInfo()[1]);
        // }

        dataManager.setEdges(reader.readEdgesFromExcelFile());
        for (Edge edge : dataManager.getEdges()) {
            System.out.println(edge.getInfo()[2]);
        }*/
    }

}