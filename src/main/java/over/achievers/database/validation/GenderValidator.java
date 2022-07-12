package over.achievers.database.validation;

import over.achievers.database.model.Employee;

public class GenderValidator implements Validator{
    @Override
    public boolean isValid(Employee employee) {
        var gender = employee.getGender();
        return ((gender == 'M') || (gender == 'F'));
    }
}
