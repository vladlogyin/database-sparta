package over.achievers.database.validation;

import over.achievers.database.model.Employee;

import java.util.ArrayList;
import java.util.Collection;

public class DOBValidator implements Validator{
    @Override
    public boolean isValid(Employee employee) {
        if(employee.getDateOfBirth().before(java.util.Calendar.getInstance().getTime()))
        {
            return true;
        }
        failed.add(employee);
        return false;
    }

    public DOBValidator()
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
        return "Date of birth";
    }
}
