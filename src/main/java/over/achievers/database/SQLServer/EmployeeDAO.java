package over.achievers.database.SQLServer;


import over.achievers.database.model.Employee;
import over.achievers.database.parsing.Parser;

import java.sql.*;
import java.util.List;

public class EmployeeDAO {

    private static final String INSERT_SQL_STATEMENT = "INSERT INTO employee (emp_number,name_preference,first_name,middle_name,last_name,gender,email,date_of_birth,joining_date,salary) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE emp_number=?";
    private static final String TRUNCATE_TABLE = "truncate table employee";

    public static void saveFromCollectionParallel(Collection<Employee> employeeList)
    {


        for(int threadCount=1;threadCount<Runtime.getRuntime().availableProcessors()*2+1;threadCount*=2) {
            long startNano = System.nanoTime();
            try {
                ConnectionFactory.getConnection().setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ThreadPool.forEach(employeeList, (employee) -> {
                saveEmployee(employee);
            }, 2);

            try {
                    ConnectionFactory.getConnection().commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            long nanoDiff = System.nanoTime()-startNano;
            double milliSeconds = nanoDiff/1E6;
            System.out.println("Threads: " + threadCount + " time spent:" + milliSeconds+"ms");
        }
    }

    public static void saveFromCollection(Collection<Employee> employeeList, Boolean autoCommit) {
        try {
            ConnectionFactory.getConnection().setAutoCommit(autoCommit);
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
        Connection conn = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(TRUNCATE_TABLE);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveEmployee(Employee employee) {
        Connection conn = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL_STATEMENT);
            preparedStatement.setInt(1, employee.getEmpNumber());
            preparedStatement.setString(2, String.valueOf((employee.getNamePreference())));
            preparedStatement.setString(3, employee.getFirstName());
            preparedStatement.setString(4, String.valueOf(employee.getMiddleName()));
            preparedStatement.setString(5, employee.getLastName());
            preparedStatement.setString(6, String.valueOf(employee.getGender()));
            preparedStatement.setString(7, employee.getEmail());
            preparedStatement.setDate(8, employee.getDateOfBirth());
            preparedStatement.setDate(9, employee.getJoiningDate());
            preparedStatement.setInt(10, employee.getSalary());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Employee getEmployeeByID (Integer id){
        Connection conn = ConnectionFactory.getConnection();
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
            throw new RuntimeException(e);
        }
    }
}

