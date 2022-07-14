package over.achievers.database.viewer;

import org.apache.logging.log4j.core.util.JsonUtils;
import over.achievers.database.SQLServer.EmployeeDAO;
import over.achievers.database.model.Employee;
import over.achievers.database.model.Logger;

import java.util.Scanner;

public class MainViewer {
    static Scanner scanner = new Scanner(System.in);
    public static void dataLoadedMessage(int addedNum, int filteredNum){
        System.out.println(addedNum + " records successfully added to database.");
        System.out.println(filteredNum + " records were filtered out due to invalid field entries");
    }
    public static int getEmpId(){
        while (true){
            try {
                return getId();
            } catch (Exception e) {
                Logger.debug(e.getMessage());
                System.out.println("Try again.\n");
            }
        }
    }
    static int getId() throws NotNaturalException {
        System.out.println("Enter the id of the employee record you want to view.");
        try{
            Integer userChoice = Integer.parseInt(scanner.nextLine());
            if (userChoice < 0){
                System.out.println("Only natural numbers accepted");
                throw new NotNaturalException("Not a natural number");
            } else
                return userChoice;
        }  catch (NumberFormatException e){
            System.out.println("invalid number");
            throw new NumberFormatException(e.getMessage());
        }
    }
    public static void displayUser(Employee emp){
        System.out.println(emp.toString());
    }
    public static int getUserThreadCount(){
        System.out.println("How many threads would you like to use?");
        int userChoice = Integer.parseInt(scanner.nextLine());

        return userChoice;
    }
}
