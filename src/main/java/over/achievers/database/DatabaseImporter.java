package over.achievers.database;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseImporter {
    /**
     *
     * @param inputCSV
     * @param rejectedCSV all the rejected entries are output to this stream
     */
    public DatabaseImporter(InputStream inputCSV, OutputStream rejectedCSV)
    {
        //TODO implement constructor
    }

    public DatabaseImporter(InputStream inputCSV) throws NullPointerException {
        this(inputCSV, System.out);
    }

    public DatabaseImporter() throws NullPointerException {
        this(System.in);
    }

    /**
     * PLACEHOLDER
     * Once we have an idea what DB interface we'll be using, this'll be implemented.
     * Or if you're daring...
     * @param database
     */
    public void migrateTo(Class database)
    {

    }

}
