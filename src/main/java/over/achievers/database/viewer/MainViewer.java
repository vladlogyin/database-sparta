package over.achievers.database.viewer;

import java.util.Scanner;

public class MainViewer {

    Scanner scanner = new Scanner(System.in);


    public String getUserChoice(){
        System.out.println("What would you like?\n1. Move data from file\n2. Input a new employee record\n3. Get an employee record");
        String choice = scanner.nextLine();
        return choice;
    }

}
