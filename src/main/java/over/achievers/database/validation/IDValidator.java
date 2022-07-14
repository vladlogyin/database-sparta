package over.achievers.database.validation;

import over.achievers.database.model.Employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IDValidator implements Validator{
    Map<Integer,Boolean> previousIDs;
    Collection<Employee> failed;
    public IDValidator()
    {
        previousIDs = new HashMap<>();
        failed = new ArrayList<>();
    }
    @Override
    public boolean isValid(Employee employee) {
        if(employee.getEmpNumber()<0)
        {
            failed.add(employee);
            return false;
        }
        if(previousIDs.containsKey(employee.getEmpNumber()))
        {
            failed.add(employee);
            return false;
        }
        previousIDs.put(employee.getEmpNumber(),true);
        return true;

    }

    @Override
    public Collection<Employee> getFailed() {
        return failed;
    }

    @Override
    public String getName() {
        return "Employee number";
    }
}
