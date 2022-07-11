package over.achievers.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String filenameCSV="asd.csv";
        InputStream inputCSV = new FileInputStream(filenameCSV);
        Scanner sc = new Scanner(inputCSV);
        while(sc.hasNextLine())
            
        //DatabaseImporter dbImporter = new DatabaseImporter(inputCSV);
    }
}