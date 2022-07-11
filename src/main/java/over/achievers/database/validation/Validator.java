package over.achievers.database.validation;

import over.achievers.database.DatabaseEntry;
import over.achievers.database.model.Employee;

public interface Validator {
    /**
     * Validates certain aspects of a database entry
     * @param Employee Database entry
     * @return true if the entry is valid
     */
    boolean isValid(Employee employee);
}
