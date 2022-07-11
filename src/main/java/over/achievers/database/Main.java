package over.achievers.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String filenameCSV="asd.csv";
        InputStream inputCSV = new FileInputStream(filenameCSV);
        DatabaseImporter dbImporter = new DatabaseImporter(inputCSV);
    }
}