package xmlparser;

public class FileFactory {
    public NewFile createFile(String type) {
        if (type.equals("csv")) {
            return new CSVFile();
//        } else if (type.equals("xls")||type.equals("xlsx")) {
//            return new ExcelFile();
        } else {
            throw new IllegalArgumentException("Unknown file: " + type);
        }
    }
}
