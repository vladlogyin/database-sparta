package over.achievers.database.validation;

import over.achievers.database.model.Employee;
//  JoiningDate: cannot be before the dateOfBirth and cannot be in the future;

public class DateValidator implements Validator{
    @Override
    public boolean isValid(Employee employee) {
        boolean joiningDateAfterDoB = employee.getJoiningDate().after(employee.getDateOfBirth());
        boolean joiningDateBeforeFuture = employee.getJoiningDate().before(java.util.Calendar.getInstance().getTime());
        return joiningDateAfterDoB && joiningDateBeforeFuture;
    }
}
