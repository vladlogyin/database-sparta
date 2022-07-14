package over.achievers.database.validation;

import over.achievers.database.model.Employee;

import java.util.ArrayList;
import java.util.Collection;

public class GenderValidator implements Validator{
    @Override
    public boolean isValid(Employee employee) {
        var gender = employee.getGender();
        if ((gender == 'M') || (gender == 'F'))
        {
            return true;
        }
        failed.add(employee);
        return false;
    }

    public GenderValidator()
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
        return "Gender";
    }

}
