package over.achievers.database.validation;

import over.achievers.database.model.Employee;

import java.util.Collection;

public interface Validator {
    /**
     * Validates certain aspects of a database entry
     * @param employee Database entry
     * @return true if the entry is valid
     */
    boolean isValid(Employee employee);
    Collection<Employee> getFailed();
    String getName();
}
