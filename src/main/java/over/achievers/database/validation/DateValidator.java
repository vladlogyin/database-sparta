package over.achievers.database.validation;

import over.achievers.database.model.Employee;

public class DateValidator implements Validator{
    @Override
    public boolean isValid(Employee employee) {
        return true;
    }
}
