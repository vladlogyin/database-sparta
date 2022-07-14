package over.achievers.database.SQLServer;


import over.achievers.database.model.Employee;

import java.sql.*;
import java.util.Arrays;
import java.util.Collection;

public class EmployeeDAO {

    private static final String INSERT_SQL_STATEMENT = "INSERT INTO employee (emp_number,name_preference,first_name,middle_name,last_name,gender,email,date_of_birth,joining_date,salary) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE emp_number=?";
    private static final String TRUNCATE_TABLE = "truncate table employee";

    /**
     * Not safe from SQL injections
     * @param employeeList
     */
    public static void saveFromCollectionParallelSuperFast(Collection<Employee> employeeList)
    {
        final int threadCount=2; // Not multi-threaded yet

        long startNano = System.nanoTime();
        Connection conn = ConnectionSingleton.getConnection();
        final PreparedStatement[] threadSpecificStatements = new PreparedStatement[threadCount];
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < threadCount; i++) {
                threadSpecificStatements[i] = conn.prepareStatement(INSERT_SQL_STATEMENT);
            }
        } catch (SQLException e) {
            // TODO IMPLEMENT LOGGING
            throw new RuntimeException("Exception thrown during statement setup:\n" + e.toString());
        }
        StringBuilder query = new StringBuilder(10_000_000);
        query.append("INSERT INTO employee (emp_number,name_preference,first_name,middle_name,last_name,gender,email,date_of_birth,joining_date,salary) VALUES ");
        boolean first=true;
        for(Employee emp : employeeList)
        {
            if(first)
            {
                first=false;

            }
            else
            {
                query.append(',');
            }
            query.append('(');
            query.append(emp.getEmpNumber());
            query.append(",'");
            query.append(emp.getNamePreference());
            query.append("','");
            query.append(emp.getFirstName());
            query.append("','");
            query.append(emp.getMiddleName());
            query.append("','");
            query.append(emp.getLastName());
            query.append("','");
            query.append(emp.getGender());
            query.append("','");
            query.append(emp.getEmail());
            query.append("','");
            query.append(emp.getDateOfBirth().toString());
            query.append("','");
            query.append(emp.getJoiningDate().toString());
            query.append("',");
            query.append(emp.getSalary());
            query.append(")");

        }
        query.append(";");
        try {
            Statement s= conn.createStatement();
            s.execute(query.toString());
            conn.commit();
        } catch (SQLException e) {
            // TODO IMPLEMENT LOGGING
        }

        long nanoDiff = System.nanoTime()-startNano;
        double milliSeconds = nanoDiff/1E6;
        System.out.println("Time spent:" + milliSeconds+"ms");
    }


    public static void saveFromCollectionMultithreadedSuperFast(Collection<Employee> employeeList) {
        final int threadCount = 4;
        final Connection[] dbConnections = new Connection[threadCount];
        final Thread[] threads = new Thread[threadCount];

        long startNano = System.nanoTime();

        // Assign each "thread" a new connection to the database.
        try {
            for (int i = 0; i < dbConnections.length; i++) {
                dbConnections[i] = new ConnectionFactory().getConnection();
                dbConnections[i].setAutoCommit(false);
            }
        } catch (SQLException e) {
            // TODO IMPLEMENT LOGGING
        }

        // Assign each "thread" a sub-collection to process
        final Employee[] array = employeeList.toArray(new Employee[0]);

        for (int i = 0; i < threads.length; i++) {
            final int arrayBegin = employeeList.size() / threadCount * i;
            final int arrayEnd = employeeList.size() / threadCount * (i + 1);
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
                    // TODO IMPLEMENT LOGGING
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

        for (Connection c : dbConnections) {
            try {
                c.close();
            } catch (SQLException e) {
                // TODO IMPLEMENT LOGGING
            }
        }

        long nanoDiff = System.nanoTime() - startNano;
        double milliSeconds = nanoDiff / 1E6;
        System.out.println("Running with " + threadCount + " threads. Took " + milliSeconds + "ms");
    }

    public static void saveFromCollectionParallel(Collection<Employee> employeeList) {
        final int threadCount = 2;

        long startNano = System.nanoTime();
        final Connection conn = ConnectionSingleton.getConnection();
        final PreparedStatement[] threadSpecificStatements = new PreparedStatement[threadCount];
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < threadCount; i++) {
                threadSpecificStatements[i] = conn.prepareStatement(INSERT_SQL_STATEMENT);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception thrown during statement setup:\n" + e.toString());
        }

        ThreadPool.forEach(employeeList, (employee,threadID) -> {
            saveEmployee(employee,threadSpecificStatements[threadID]);
        }, threadCount);

        try {
            ThreadPool.forEach(Arrays.asList(threadSpecificStatements), (statement, threadID) -> {
                try {
                    statement.executeBatch();
                    statement.close();
                    conn.commit();

                }
                catch (SQLException e)
                {
                    throw new RuntimeException("Exception thrown in thread "+threadID+" during statement execution:\n" + e.toString());
                }
            }, threadCount);

        }
        catch (RuntimeException e)
        {
            throw new RuntimeException("Exception thrown during statement execution:\n" + e.toString());
        }

        long nanoDiff = System.nanoTime()-startNano;
        double milliSeconds = nanoDiff/1E6;
        System.out.println("Threads: " + threadCount + " time spent:" + milliSeconds+"ms");
    }

    public static void saveFromCollection(Collection<Employee> employeeList, Boolean autoCommit) {
        try {
            ConnectionSingleton.getConnection().setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Employee emp : employeeList) {
            saveEmployee(emp);
        }
    }

    public static void saveFromCollection(Collection<Employee> employeeList) {
        saveFromCollection(employeeList, false);
    }

    public static void truncateTable(){
        Connection conn = new ConnectionFactory().getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(TRUNCATE_TABLE);
            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            // TODO IMPLEMENT LOGGING
        }
    }

    public static void saveEmployee(Employee employee) {
        Connection conn = ConnectionSingleton.getConnection();
        PreparedStatement saveEmployeeStatement;
        try {
            saveEmployeeStatement = conn.prepareStatement(INSERT_SQL_STATEMENT);
            saveEmployee(employee, saveEmployeeStatement);
            saveEmployeeStatement.executeBatch();
        } catch (SQLException e) {
            // TODO IMPLEMENT LOGGING
            throw new RuntimeException(e);
        }
    }

    public static void saveEmployee(Employee employee, PreparedStatement statement) {
        Connection conn = ConnectionSingleton.getConnection();
        try {
            synchronized (statement) {
                statement.setInt(1, employee.getEmpNumber());
                statement.setString(2, String.valueOf((employee.getNamePreference())));
                statement.setString(3, employee.getFirstName());
                statement.setString(4, String.valueOf(employee.getMiddleName()));
                statement.setString(5, employee.getLastName());
                statement.setString(6, String.valueOf(employee.getGender()));
                statement.setString(7, employee.getEmail());
                statement.setDate(8, employee.getDateOfBirth());
                statement.setDate(9, employee.getJoiningDate());
                statement.setInt(10, employee.getSalary());
                statement.addBatch();
            }
        } catch (SQLException e) {
            // TODO IMPLEMENT LOGGING
            throw new RuntimeException(e);
        }
    }

    public static Employee getEmployeeByID (Integer id){
        Connection conn = ConnectionSingleton.getConnection();
        try {
            Statement statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_EMPLOYEE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            StringBuffer sb = new StringBuffer();
            for (int i = 1; i < 11; i++) {
                sb.append(rs.getString(i)).append(" ");

            }
        } catch (SQLException e) {
            // TODO IMPLEMENT LOGGING
            throw new RuntimeException(e);
        }
        return null;
    }
}

