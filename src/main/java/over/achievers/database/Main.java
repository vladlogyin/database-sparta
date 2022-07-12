package over.achievers.database;

import over.achievers.database.SQLServer.EmployeeDAO;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {


//        String filenameCSV="src\\main\\java\\over\\achievers\\database\\EmployeeRecords2.csv";
//        InputStream inputCSV = new FileInputStream(filenameCSV);
//        Scanner sc = new Scanner(inputCSV);
//        Collection<String> inputData = new ArrayList<>();
//        sc.nextLine();
//        while(sc.hasNextLine())
//        {
//            inputData.add(sc.nextLine().trim());
//        }
//        // At this point we have a collection full of employee data 🗸
//        Collection<Employee> toBeValidated = new ArrayList<>();
//        Parser par = new Parser();
//        for(String line : inputData)
//        {
//            toBeValidated.add(par.parse(line));
//        }
//        // At this point we have a collection full of Employee objects 🗸
//        Collection<Employee> valid = new ArrayList<>(); // stores valid employee data
//        Collection<Employee> invalid = new ArrayList<>(); // stores invalid data, filtered out of employee collection
//        Validator[] validators=new Validator[]{/*new IDValidator(),*/new EmailValidator()};
//        for(Employee emp : toBeValidated)
//        {
//            if(emp.isValid(validators))
//            {
//                valid.add(emp);
//            }
//            else
//            {
//                invalid.add(emp);
//            }
//        }
//        System.out.println("No. valid: "+ valid.size()+"\nNo. invalid: " + invalid.size());
        // At this point we have two collections full of Employee objects 🗸
        //sendToDatabase(valid);
    }
}