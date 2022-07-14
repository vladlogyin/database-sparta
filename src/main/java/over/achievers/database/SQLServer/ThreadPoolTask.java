package over.achievers.database.SQLServer;

import java.sql.SQLException;

public interface ThreadPoolTask<E> {
    void run(E param, int threadID);

}
