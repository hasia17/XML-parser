
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


public class Runner {
    
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        
        //for(String a : args) {
        
        //String url = a;
        XMLParser parser = new XMLParser("https://www.w3schools.com/xml/cd_catalog.xml", "myFile.xml", "myFile.csv", "myFile.xlsx");
        parser.run();

    }
}
