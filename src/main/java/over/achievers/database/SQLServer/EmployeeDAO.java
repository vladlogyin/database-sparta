package over.achievers.database.SQLServer;


import over.achievers.database.model.Employee;
import over.achievers.database.model.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class EmployeeDAO {

    private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE emp_number=?";
    private static final String TRUNCATE_TABLE = "truncate table employee";

    public static void saveFromCollectionMultithreadedSuperFast(Collection<Employee> employeeList, int threadCount)  throws SQLException{
        final Connection[] dbConnections = new Connection[threadCount];
        final Thread[] threads = new Thread[threadCount];

        final Collection<Throwable> threadExceptions = new ArrayList();

        long startNano = System.nanoTime();

        for (int i = 0; i < dbConnections.length; i++) {
            dbConnections[i] = new ConnectionFactory().getConnection();
            dbConnections[i].setAutoCommit(false);
        }

        // Assign each "thread" a sub-collection to process
        final Employee[] array = employeeList.toArray(new Employee[0]);

        for (int i = 0; i < threads.length; i++) {
            final int arrayBegin = employeeList.size() * i / threadCount;
            final int arrayEnd = employeeList.size()* (i + 1) / threadCount;
            final int threadID = i;

            threads[threadID] = new Thread(() -> {
                StringBuilder query = new StringBuilder(10_000_000);
                query.append("INSERT INTO employee (emp_number,name_preference,first_name,middle_name,last_name,gender,email,date_of_birth,joining_date,salary) VALUES ");
                boolean first = true;
                for (int j = arrayBegin; j < arrayEnd; j++) {
                    if (first) {
                        first = false;

                    } else {
                        query.append(',');
                    }
                    query.append('(');
                    query.append(array[j].getEmpNumber());
                    query.append(",'");
                    query.append(array[j].getNamePreference());
                    query.append("','");
                    query.append(array[j].getFirstName());
                    query.append("','");
                    query.append(array[j].getMiddleName());
                    query.append("','");
                    query.append(array[j].getLastName());
                    query.append("','");
                    query.append(array[j].getGender());
                    query.append("','");
                    query.append(array[j].getEmail());
                    query.append("','");
                    query.append(array[j].getDateOfBirth().toString());
                    query.append("','");
                    query.append(array[j].getJoiningDate().toString());
                    query.append("',");
                    query.append(array[j].getSalary());
                    query.append(")");
                }
                query.append(";");

                try {
                    Statement s = dbConnections[threadID].createStatement();
                    s.execute(query.toString());
                    dbConnections[threadID].commit();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            threads[threadID].setUncaughtExceptionHandler((th, ex)-> {
                synchronized (threadExceptions)
                {
                    threadExceptions.add(ex);
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to finish before closing connections and calculating time.
        boolean keepWaiting=true;
        while(keepWaiting){
            keepWaiting=false;
            for(Thread th : threads)
            {
                keepWaiting |= th.isAlive();
            }
        }
        // All threads are dead

        for(Throwable th : threadExceptions)
        {
            throw new SQLException(th);
        }

        for (Connection c : dbConnections) {
            try {
                c.close();
            } catch (SQLException e) { // Realistically this would not happen.
                Logger.warn("Could not close connection: " + e.getMessage());
            }
        }

        long nanoDiff = System.nanoTime() - startNano;
        double milliSeconds = nanoDiff / 1E6;
        Logger.info("Running with " + threadCount + " threads. Took " + milliSeconds + "ms");
    }

    public static void truncateTable() throws SQLException{
        Connection conn = new ConnectionFactory().getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(TRUNCATE_TABLE);
        preparedStatement.execute();
        conn.close();
    }

    public static Employee getEmployeeByID (Integer id) throws SQLException{
        Connection conn = new ConnectionFactory().getConnection();
            Statement statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_EMPLOYEE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return new Employee()
                    .setEmpNumber(rs.getInt(1))
                    .setNamePreference(rs.getString(2))
                    .setFirstName(rs.getString(3))
                    .setMiddleName(rs.getString(4).charAt(0))
                    .setLastName(rs.getString(5))
                    .setGender(rs.getString(6).charAt(0))
                    .setEmail(rs.getString(7))
                    .setDateOfBirth(rs.getDate(8))
                    .setJoiningDate(rs.getDate(9))
                    .setSalary(rs.getInt(10));
    }
}

