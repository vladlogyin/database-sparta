package over.achievers.database.validation;

import over.achievers.database.DatabaseEntry;

public interface Validator {
    /**
     * Validates certain aspects of a database entry
     * @param entry Database entry
     * @return true if the entry is valid
     */
    boolean isValid(DatabaseEntry entry);
}
