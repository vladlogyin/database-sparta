package over.achievers.database.viewer;

import java.util.Scanner;

public class MainViewer {

    Scanner scanner = new Scanner(System.in);

    public String getFilePathFromUser(){
        System.out.print("Please enter the path to your employee.csv\n> ");
        return scanner.nextLine();
    }

    public void informProcessingStart(){
        System.out.println("Processing employee data.");
    }

    public void informProcessingComplete(){
        System.out.println("Finished processing employee data.");
    }

    private String getUserChoice(){
        System.out.println("What action would you like to preform?\n1. Move data from file\n2. Input a new employee record\n3. Get an employee record");
        String choice = scanner.nextLine();
        return choice;
    }

    public void displayStatistics(int unique, int clean, int duplicates, int missingFields){
        System.out.println("Migration Statistics");
        System.out.format("\n%-34s: %-10d\n", "Unique records" ,unique);
        System.out.format("%-34s: %-10d\n", "Clean records" ,clean);
        System.out.format("%-34s: %-10d\n", "Duplicate records" ,duplicates);
        System.out.format("%-34s: %-10d\n", "Records with missing fields",missingFields);
    }

}
