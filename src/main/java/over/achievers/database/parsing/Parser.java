package over.achievers.database.parsing;

import over.achievers.database.model.Employee;

import java.sql.Date;

public class Parser {
    public Employee parse(String line) throws InvalidFormatException, NullPointerException
    {
        if(line==null)
        {
            throw new NullPointerException();
        }
        int tokenIndex=0;
        try {
            String[] tokens = line.split(",");
            int employeeNumber = Integer.parseInt(tokens[tokenIndex++]);
            String namePrefix = tokens[tokenIndex++];
            String nameFirst = tokens[tokenIndex++];
            char nameMiddle = tokens[tokenIndex++].charAt(0);
            String nameLast = tokens[tokenIndex++];
            char gender = tokens[tokenIndex++].charAt(0);
            String email = tokens[tokenIndex++];
            Date dob = DateParser.parse(tokens[tokenIndex++]);
            Date joinDate = DateParser.parse(tokens[tokenIndex++]);
            int salary = Integer.parseInt(tokens[tokenIndex++]);
            Employee retVal = new Employee(employeeNumber, namePrefix, nameFirst, nameMiddle, nameLast, gender, email, dob, joinDate, salary);
            return retVal;
        }
        catch (RuntimeException e)
        {
            throw new InvalidFormatException("token index(0-based):"+tokenIndex + "\n" + e.getMessage());
        }
    }
}
