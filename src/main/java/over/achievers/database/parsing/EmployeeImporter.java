package over.achievers.database.parsing;

import over.achievers.database.model.Employee;
import over.achievers.database.validation.Validator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class EmployeeImporter {
    private Collection<String> invalidLines;

    private Collection<Employee> validEmployees;

    private Collection<Employee> invalidEmployees;
    private EmployeeImporter()
    {
        invalidLines = new ArrayList<>();
        validEmployees = new ArrayList<>();
        invalidEmployees = new ArrayList<>();
    }
    public static EmployeeImporter fromCSV(String path, Validator[] validators) throws FileNotFoundException, SecurityException
    {
        EmployeeImporter retVal = new EmployeeImporter();
        FileInputStream inputCSV = new FileInputStream(path);
        Scanner sc = new Scanner(inputCSV);
        Parser parser = new Parser();

        sc.nextLine();

        while(sc.hasNextLine())
        {
            String line = sc.nextLine().trim();
            try {
                Employee emp = parser.parse(line);
                if(emp.isValid(validators))
                {
                    retVal.validEmployees.add(emp);
                }
                else
                {
                    retVal.invalidEmployees.add(emp);
                }
            }
            catch (InvalidFormatException e)
            {
                // TODO add logging here
                retVal.invalidLines.add(line);
            }

        }
        return retVal;
    }

    public Collection<Employee> getInvalidEmployees()
    {
        return invalidEmployees;
    }

    public Collection<Employee> getValidEmployees()
    {
        return validEmployees;
    }

    public Collection<String> getInvalidLines()
    {
        return invalidLines;
    }
}
