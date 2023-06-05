package Main;

import java.io.IOException;

import Data.DataManager;
import Data.Node;
import GUI.GUI;
import Utils.Reader;

public class Main {
    private DataManager dataManager;


    public static void main(String[] args) throws IOException {
        //GUI windowGUI = new GUI();
        var nodes = Reader.readNodesFromExcelFile();
        for (Node node : nodes) {
            System.out.println(node.getInfo()[1]);
        }
    }

}