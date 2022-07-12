package over.achievers.database;

import over.achievers.database.model.Employee;
import over.achievers.database.validation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Parser par2 = new Parser();
        Employee employee = par2.parse("178566,Mrs.,Juliette,M,Rojo,F,juliette.rojo@yahoo.co.uk,5/8/1967,6/4/2011,193912");

        String filenameCSV="src\\main\\java\\over\\achievers\\database\\EmployeeRecords2.csv";
        InputStream inputCSV = new FileInputStream(filenameCSV);
        Scanner sc = new Scanner(inputCSV);
        Collection<String> inputData = new ArrayList<>();
        sc.nextLine();
        while(sc.hasNextLine())
        {
            inputData.add(sc.nextLine().trim());
        }
        // At this point we have a collection full of employee data 🗸
        Collection<Employee> toBeValidated = new ArrayList<>();
        Parser par = new Parser();
        for(String line : inputData)
        {
            toBeValidated.add(par.parse(line));
        }
        // At this point we have a collection full of Employee objects 🗸
        Collection<Employee> valid = new ArrayList<>(); // stores valid employee data
        Collection<Employee> invalid = new ArrayList<>(); // stores invalid data, filtered out of employee collection
        Validator[] validators=new Validator[]{/*new IDValidator(),*/new EmailValidator()};
        for(Employee emp : toBeValidated)
        {
            if(emp.isValid(validators))
            {
                valid.add(emp);
            }
            else
            {
                invalid.add(emp);
            }
        }
        System.out.println("No. valid: "+ valid.size()+"\nNo. invalid: " + invalid.size());

        // At this point we have two collections full of Employee objects 🗸

        //sendToDatabase(valid);
    }
    static class Parser
    {
        //Emp ID	Name Prefix	First Name	Middle Initial	Last Name	Gender	E Mail	Date of Birth	Date of Joining	Salary
        //178566,Mrs.,Juliette,M,Rojo,F,juliette.rojo@yahoo.co.uk,5/8/1967,6/4/2011,193912
        public Employee parse(String line)
        {
            String[] tokens = line.split(",");
            int employeeNumber = Integer.parseInt(tokens[0]);
            String namePrefix = tokens[1];
            String nameFirst = tokens[2];
            char nameMiddle = tokens[3].charAt(0);
            String nameLast = tokens[4];
            char gender = tokens[5].charAt(0);
            String email = tokens[6];
            // tokens[7] - DOB MM/dd/YYYY
            // tokens[8] - Join date MM/dd/YYYY
            int salary = Integer.parseInt(tokens[9]);
            Employee retVal = new Employee(employeeNumber, namePrefix, nameFirst, nameMiddle, nameLast, gender, email, new java.sql.Date(0), new java.sql.Date(0),salary);
            return retVal;
        }
    }
}