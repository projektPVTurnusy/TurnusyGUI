package Util;

import Data.DataManager;
import Data.Edge;
import Data.Node;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Reader {
    
    private DataManager dataManager;

    public Reader(DataManager manager){
        this.dataManager = manager;
    }

    public ArrayList<Node> readNodesFromExcelFile() throws IOException{
        
        ArrayList<Node> readedNodes = new ArrayList<Node>();
        
        try {
            FileInputStream inputStream = new FileInputStream(new File("src/Resources/vrcholy.xlsx"));
            
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);   
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file  

            int i = 0;
            while(itr.hasNext()){
                
                Row row = itr.next();

                if(i == 0 ){
                    i++;
                    continue;
                }

                Node node = new Node((int)row.getCell(0).getNumericCellValue(), 
                                      (double)row.getCell(2).getNumericCellValue(), 
                                      (double)row.getCell(3).getNumericCellValue(), 
                                      row.getCell(1).getStringCellValue());
                
                readedNodes.add(node);
            }

            return readedNodes;

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return readedNodes;
        }

    }

    public ArrayList<Edge> readEdgesFromExcelFile() throws IOException{
        
        if(dataManager.getNodeSize() == 0){
            return null;
        }
        
        ArrayList<Edge> readedEdges = new ArrayList<Edge>();
        
        try {
            FileInputStream inputStream = new FileInputStream(new File("src/Resources/hrany.xlsx"));
            
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);   
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file  

            int i = 0;
            int index = 0;
            while(itr.hasNext()){
                
                Row row = itr.next();

                if(i == 0 ){
                    i++;
                    continue;
                }
                
                int nodeStartId =  (int)row.getCell(0).getNumericCellValue();
                int nodeEndId =  (int)row.getCell(2).getNumericCellValue();

                Node startNode = dataManager.getNodes().stream()
                                .filter(n -> n.getId() == nodeStartId)
                                .findAny()
                                .orElse(null);

                Node endNode = dataManager.getNodes().stream()
                                .filter(n -> n.getId() == nodeEndId)
                                .findAny()
                                .orElse(null);

                if(endNode == null || startNode == null){
                    System.out.println("Nepodarilo sa nacitat hranu so zac. vrcholom "+ nodeStartId +" a koncovym "+nodeEndId);
                }
                else{
                    Edge edge = new Edge(index++, startNode, endNode, (int)row.getCell(4).getNumericCellValue());
                    if(startNode.addEdge(edge) && endNode.addEdge(edge))
                        readedEdges.add(edge);
                    else
                    {
                        index--;
                    }
                }

                i++;
            }

            return readedEdges;

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return readedEdges;
        }

    }
}
