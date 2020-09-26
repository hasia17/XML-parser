
package xmlparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;

public class XMLParser {

    private String URL;
    private String XMLFileName;
    private String CSVFileName;
    private String ExcelFileName;
    
    public XMLParser(String URL, String XMLFileName, String CSVFileName, String ExcelFileName) {
        this.URL = URL;
        this.XMLFileName = XMLFileName;
        this.CSVFileName = CSVFileName;
        this.ExcelFileName = ExcelFileName;
        
    }  
    
    
    public void run() throws IOException, ParserConfigurationException, SAXException {
        downloadUsingNIO();
        Document document = createDocumentBuilderFactory();
        String rootName = document.getDocumentElement().getNodeName();
        ArrayList nameList = getNameList(document, rootName);
        writeCSV(document, nameList);
        writeExcelFile(document, nameList);
    }
    
    
    
     private void downloadUsingNIO() throws IOException {
        URL url = new URL(URL);
        ReadableByteChannel byteChannel = Channels.newChannel(url.openStream());
        FileOutputStream output = new FileOutputStream(XMLFileName);
        output.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
        output.close();
        byteChannel.close();
    } 
     
     
     private Document createDocumentBuilderFactory() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(XMLFileName);
        document.getDocumentElement().normalize();
        return document;
    }
     
     
    
     
      private ArrayList getNameList(Document document, String rootName) {
        NodeList nodeList = document.getElementsByTagName("*");
        Node node;
        ArrayList<String> list = new ArrayList<String>();

        for (int i=0; i<nodeList.getLength(); i++) {
            node = nodeList.item(i);
            String name  = node.getNodeName();
            if (!list.contains(name) && name != rootName) {
                list.add(name);
            }  
        }
        return list;    
    }
      
      
      
      private void writeCSV(Document document, ArrayList nameList) throws IOException {  
        NodeList list = document.getElementsByTagName((String) nameList.get(0));
        File file = new File(CSVFileName);
        FileWriter csvFile = new FileWriter(file);
        
        for (int k = 1; k < nameList.size(); k++) {
            String nodeName1 = (String) nameList.get(k);
            csvFile.append(nodeName1);
            if (k != nameList.size()-1) {
                csvFile.append(", "); 
            }
        }
           
        csvFile.append("\n");
           
        for (int j = 0; j <list.getLength(); j++) {
            Node node = list.item(j);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                
                for (int i = 1; i < nameList.size(); i++) {
                    String nodeName = (String) nameList.get(i);
                    String value = eElement.getElementsByTagName(nodeName).item(0).getTextContent(); 
                    csvFile.append(value);
                    if (i != nameList.size()-1) {
                        csvFile.append(", ");
                    }
                }
            }           
            csvFile.append("\n");
        }
        csvFile.flush();
        csvFile.close();
    }  
      
      
      private void writeExcelFile(Document document, ArrayList nameList) throws FileNotFoundException, IOException {
          
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Java Books");
        NodeList list = document.getElementsByTagName((String) nameList.get(0));
 
        int rowCounter = 0;
        Row row = sheet.createRow(rowCounter);
         for (int k = 1; k < nameList.size(); k++) {
            String nodeName1 = (String) nameList.get(k);
            
            Cell cell = row.createCell(k-1);
            cell.setCellValue((String) nodeName1);
         }
         
         
         for (int j = 0; j <list.getLength(); j++) {
            Node node = list.item(j);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                Row newRow = sheet.createRow(++rowCounter);
                
                for (int i = 1; i < nameList.size(); i++) {
                    String nodeName = (String) nameList.get(i);
                    String value = eElement.getElementsByTagName(nodeName).item(0).getTextContent();
                    Cell cell = newRow.createCell(i-1);
                    cell.setCellValue((String) value);
                }
            }           
         }  
        FileOutputStream outputStream = new FileOutputStream(ExcelFileName);
        workbook.write(outputStream);
      }
         
    








}



    
    