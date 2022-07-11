package over.achievers.database;

import over.achievers.database.validation.Validator;

public class DatabaseEntry {
    // TODO look at all of the fields required by a database entry eg. name gender etc and add them here
    public boolean isValid(Validator[] validators)
    {
        for(Validator v : validators)
        {
            if(!v.isValid(this))
            {
                return false;
            }
        }
        return true;
    }
}
