package over.achievers.database.SQLServer;


import over.achievers.database.model.Employee;

import java.sql.*;
import java.util.List;

public class EmployeeDAO implements DAOInterface {
    private ConnectionFactory connectionFactory;

    private final String INSERT_SQL_STATEMENT = "INSERT INTO employee (emp_number,name_preference,first_name,middle_name,last_name,gender,email,date_of_birth,joining_date,salary) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE emp_number=?";

    public void saveFromCollection(List<Employee> employeeList){
        for (Employee emp : employeeList){
            saveEmployee(emp);
        }
    }
    @Override
    public void saveEmployee(Employee employee) {
        Connection conn = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL_STATEMENT);
            preparedStatement.setInt(1,employee.getEmpNumber());
            preparedStatement.setString(2, String.valueOf((employee.getNamePreference())));
            preparedStatement.setString(3,employee.getFirstName());
            preparedStatement.setString(4, String.valueOf(employee.getMiddleName()));
            preparedStatement.setString(5,employee.getLastName());
            preparedStatement.setString(6, String.valueOf(employee.getGender()));
            preparedStatement.setString(7,employee.getEmail());
            preparedStatement.setDate(8,employee.getDateOfBirth());
            preparedStatement.setDate(9,employee.getJoiningDate());
            preparedStatement.setInt(10,employee.getSalary());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getEmployeeByID(Integer id) {

        Connection conn = ConnectionFactory.getConnection();
        try {
            Statement statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_EMPLOYEE_BY_ID);
            preparedStatement.setInt(1,id);
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
