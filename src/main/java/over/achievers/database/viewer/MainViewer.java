package over.achievers.database.viewer;

import over.achievers.database.model.Employee;
import over.achievers.database.model.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

public class MainViewer {
    static Scanner scanner = new Scanner(System.in);
    public static void startMessage(){
        System.out.println("Writing to database");
    }
    public static void dataLoadedMessage(int addedNum, int filteredNum){
        System.out.println(addedNum + " records successfully added to database.");
        System.out.println(filteredNum + " records were filtered out due to invalid field entries\n");
    }
    public static void printRunTime(double t){
        System.out.println("Operation took " + t +" milliseconds");
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
            if (userChoice < 1){
                System.out.println("Only natural numbers accepted, one or greater");
                throw new NotNaturalException("Not a natural number");
            } else
                return userChoice;
        }  catch (NumberFormatException e){
            System.out.println("invalid number");
            throw new NumberFormatException(e.getMessage());
        }
    }
    public static boolean isYes(String input){
        switch (input.toLowerCase().trim()){
            case "yes", "yeah", "ye", "y", "aye":
                return true;
            default:
                return false;
        }
    }
    public static boolean getViewChoice(){
        System.out.println("Would you like to view the invalid records?");
        String userInput = scanner.nextLine();
        return isYes(userInput);
    }

    public static void showData(Collection<?> employees, String msg){
        System.out.println(msg);
        if (employees.size() == 0){
            System.out.println("None\n");
            return;
        }
        Iterator rs = employees.iterator();
        while(rs.hasNext()) {
            for (int i = 0; i < 15 && rs.hasNext(); i++){
                Employee emp =  (Employee) rs.next();
                System.out.println(emp.toString());
            }
            if (rs.hasNext())
                System.out.println("Enter to view more, q to quit");
            String userChoice = scanner.nextLine().trim();
            if(userChoice.equals("q")){
                break;
            } else{
                continue;
            }
        }
    }

    public static void displayEmployee(Employee emp){
        System.out.println(emp.toString() + "\n");
    }
    public static void printMessage(String msg){
        System.out.println(msg);
    }
    public static boolean viewAgain(){
        System.out.println("Would you like to view another? (y)");
        String userChoice = scanner.nextLine();
        return isYes(userChoice);
    }
    public static int getThreadChoice(){
        System.out.println("How many threads would you like to use?");
        try{
            int userChoice = Integer.parseInt(scanner.nextLine());
            if (userChoice < 1){
                System.out.println("Thread count must be greater than or equal to 1");
                throw new NotNaturalException("thread count must be equal to or greater than 1");
            }
             else
                 return userChoice;
        }  catch(Exception e){
            Logger.info("Invalid threadcount entered in getThreadChoice method from MainViewer\n" +  e.getMessage());
        }
        System.out.println("defaulting to four.");
        return 4;
    }
    public static boolean userHasConfig(){
        System.out.println("Would you like to enter new credentials?");
        String userChoice = scanner.nextLine();
        return isYes(userChoice);
    }

    public static String[] getUserCredentials(){
        String[] credentials = new String[3];
        credentials[0] = getUrl();
        credentials[1] = getUsername();
        credentials[2] = getPassword();
        return credentials;
    }
    static String getUrl(){
        System.out.println("Enter the url of the database - default - jdbc:mysql://localhost:3306/employee");
        String userChoice = scanner.nextLine();
        return userChoice;
    }
    public static String getUsername(){
        System.out.println("Enter the username of the database");
        String userChoice = scanner.nextLine();
        return userChoice;
    }
    public static String getPassword(){
        System.out.println("Enter the password of the database");
        String userChoice = scanner.nextLine();
        return userChoice;
    }
    public static boolean reloadProperties(){
        System.out.println("Problem with loading resources/database");
        System.out.println("Would you like to try loading resources/database.properties again?");
        return isYes(scanner.nextLine());
    }


}
