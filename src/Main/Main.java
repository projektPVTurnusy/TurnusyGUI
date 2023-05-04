package Main;

import Data.Node;
import Data.Edge;
import Data.DataManager;
import GUI.WindowGUI;



public class Main {
    private WindowGUI gui;
    private DataManager dataManager;


    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println("Hello world!");
        System.out.println("Hello world!");
        new Main();

    }

    private Main() {
        this.gui = new WindowGUI(this);

    }
}