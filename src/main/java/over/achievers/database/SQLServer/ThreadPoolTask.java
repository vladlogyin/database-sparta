package over.achievers.database.SQLServer;

public interface ThreadPoolTask<E> {
    void run(E param);

}
