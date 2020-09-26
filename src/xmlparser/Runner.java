
package xmlparser;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Runner {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        XMLParser parser = new XMLParser(
                "https://www.w3schools.com/xml/cd_catalog.xml",
                "myFile.xml",
                "myFile.csv",
                "myFile.xlsx"
        );
//         XMLParser parser = new XMLParser(
//            args[0] ,
//            args[1],
//            args[2],
//            args[3]
//         );

        parser.run();
    }
}
