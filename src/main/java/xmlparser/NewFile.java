package xmlparser;

import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;

public interface NewFile {

    void writeFile(Document document, ArrayList nameList, String name) throws IOException;

}
