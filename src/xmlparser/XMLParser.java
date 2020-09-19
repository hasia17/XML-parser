
package xmlparser;

import java.io.File;
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

public class XMLParser {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        
        for(String a : args) {
        
        String url = a;
     
        downloadUsingNIO(url, "myFile.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("myFile.xml");
        document.getDocumentElement().normalize();
        String rootName = document.getDocumentElement().getNodeName();
        ArrayList nameList = getNameList(document, rootName);
        writeCSV(document, nameList);
        
        }
    }
    

    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel byteChannel = Channels.newChannel(url.openStream());
        FileOutputStream output = new FileOutputStream(file);
        output.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
        output.close();
        byteChannel.close();
      
    }    
    
    
    private static ArrayList getNameList(Document document, String rootName) {
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
    
    
    private static void writeCSV(Document document, ArrayList nameList) throws IOException {  
        NodeList list = document.getElementsByTagName((String) nameList.get(0));
        File file = new File("myFile.csv");
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
        
 }   