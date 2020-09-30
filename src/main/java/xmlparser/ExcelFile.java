package xmlparser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelFile implements NewFile {

    public void writeFile(Document document, ArrayList nameList, String name) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(name+".xlsx");
        NodeList list = document.getElementsByTagName((String) nameList.get(0));

        int rowCounter = 0;
        Row row = sheet.createRow(rowCounter);
        for (int k = 1; k < nameList.size(); k++) {
            String nodeName1 = (String) nameList.get(k);

            Cell cell = row.createCell(k - 1);
            cell.setCellValue((String) nodeName1);
        }

        for (int j = 0; j < list.getLength(); j++) {
            Node node = list.item(j);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                Row newRow = sheet.createRow(++rowCounter);

                for (int i = 1; i < nameList.size(); i++) {
                    String nodeName = (String) nameList.get(i);
                    String value = eElement.getElementsByTagName(nodeName).item(0).getTextContent();
                    Cell cell = newRow.createCell(i - 1);
                    cell.setCellValue((String) value);
                }
            }
        }
        FileOutputStream outputStream = new FileOutputStream(name+".xlsx");
        workbook.write(outputStream);
    }
}
