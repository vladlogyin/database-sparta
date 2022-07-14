package over.achievers.database.validation;

import over.achievers.database.model.Employee;

import java.util.ArrayList;
import java.util.Collection;

public class DateValidator implements Validator{
    @Override
    public boolean isValid(Employee employee) {
        boolean joiningDateAfterDoB = employee.getJoiningDate().after(employee.getDateOfBirth());
        boolean joiningDateBeforeFuture = employee.getJoiningDate().before(java.util.Calendar.getInstance().getTime());
        if(joiningDateAfterDoB && joiningDateBeforeFuture)
        {
            return true;
        }
        failed.add(employee);
        return false;
    }

    public DateValidator()
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
        return "Join date";
    }
}
