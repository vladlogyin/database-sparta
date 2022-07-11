package over.achievers.database.validation;

import over.achievers.database.model.Employee;

import java.util.HashMap;
import java.util.Map;

public class IDValidator implements Validator{
    Map<Integer,Boolean> previousIDs;
    public IDValidator()
    {
        previousIDs = new HashMap<>();
    }
    @Override
    public boolean isValid(Employee employee) {
        if(previousIDs.containsKey(employee.getEmpNumber()))
            return false;
        previousIDs.put(employee.getEmpNumber(),true);
        return true;

    }
}
