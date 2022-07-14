package over.achievers.database.validation;

import over.achievers.database.model.Employee;

import java.util.ArrayList;
import java.util.Collection;

public class SalaryValidator implements Validator{
    @Override
    public boolean isValid(Employee employee) {
        if(employee.getSalary() > 0) return true;
        else failed.add(employee);
        return false;

    }



    public SalaryValidator()
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
        return "Salary";
    }
}
