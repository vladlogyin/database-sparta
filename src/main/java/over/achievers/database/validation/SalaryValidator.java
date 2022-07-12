package over.achievers.database.validation;

import over.achievers.database.model.Employee;

public class SalaryValidator implements Validator{
    @Override
    public boolean isValid(Employee employee) {
        return employee.getSalary() > 0;
    }
}
