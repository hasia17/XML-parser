
package xmlparser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

    final private String URL;
    final private String type;
    final private String name;
    final private FilesFactory factory;
    final private DocumentBuilderFactory documentBuilderFactory;

    public XMLParser(String URL, String type, String name) {
        this.URL = URL;
        this.type = type;
        this.name = name;
        this.factory = new FilesFactory();
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
    }

    public void run() throws IOException, ParserConfigurationException, SAXException {
        downloadXML();
        Document document = createDocumentBuilderFactory();
        String rootName = document.getDocumentElement().getNodeName();
        ArrayList nameList = getNameList(document, rootName);
        NewFile file = factory.createFile(type);
        file.writeFile(document, nameList, name);
    }

    private void downloadXML() throws IOException {
        URL url = new URL(URL);
        ReadableByteChannel byteChannel = Channels.newChannel(url.openStream());
        String xmlName = name + ".xml";
        FileOutputStream output = new FileOutputStream(xmlName);
        output.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
        output.close();
        byteChannel.close();
    }

    private Document createDocumentBuilderFactory() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        String xmlName = name + ".xml";
        Document document = builder.parse(xmlName);
        document.getDocumentElement().normalize();
        return document;
    }

    private ArrayList getNameList(Document document, String rootName) {
        NodeList nodeList = document.getElementsByTagName("*");
        Node node;
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            String name = node.getNodeName();
            if (!list.contains(name) && !name.equals(rootName)) {
                list.add(name);
            }
        }
        return list;
    }


}



    
    