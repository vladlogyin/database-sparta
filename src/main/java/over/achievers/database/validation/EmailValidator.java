package over.achievers.database.validation;

import over.achievers.database.model.Employee;

import java.util.ArrayList;
import java.util.Collection;

public class EmailValidator implements Validator {
    @Override
    public boolean isValid(Employee employee) {
        // RFC 5322 Official Standard Email Regex
        String ePattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(employee.getEmail());
        if(m.matches())
        {
            return true;
        }
        failed.add(employee);
        return false;
    }
    public EmailValidator()
    {
        failed = new ArrayList<>();
    }
    Collection<Employee> failed;
    @Override
    public Collection<Employee> getFailed() {
        return failed;
    }

    @Override
    public String getName() {
        return "Email";
    }
}
