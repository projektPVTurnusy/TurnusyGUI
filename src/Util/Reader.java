package Util;

import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Data.Edge;
import Data.Node;
import Data.NodeType;  

public class Reader {
    
    public static List<Node> readNodesFromExcelFile() throws IOException{
        
        List<Node> readedNodes = new ArrayList<Node>();
        
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
                                      row.getCell(1).getStringCellValue(), NodeType.UNSPECIFIED, 0);
                
                readedNodes.add(node);
            }

            return readedNodes;

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return readedNodes;
        }

    }
}
