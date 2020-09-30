package xmlparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVFile implements NewFile {


    public void writeFile(Document document, ArrayList nameList, String name) throws IOException {
        NodeList list = document.getElementsByTagName((String) nameList.get(0));
        File file = new File(name+".csv");
        FileWriter csvFile = new FileWriter(file);

        for (int k = 1; k < nameList.size(); k++) {
            String nodeName1 = (String) nameList.get(k);
            csvFile.append(nodeName1);
            if (k != nameList.size() - 1) {
                csvFile.append(", ");
            }
        }

        csvFile.append("\n");

        for (int j = 0; j < list.getLength(); j++) {
            Node node = list.item(j);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;

                for (int i = 1; i < nameList.size(); i++) {
                    String nodeName = (String) nameList.get(i);
                    String value = eElement.getElementsByTagName(nodeName).item(0).getTextContent();
                    csvFile.append(value);
                    if (i != nameList.size() - 1) {
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