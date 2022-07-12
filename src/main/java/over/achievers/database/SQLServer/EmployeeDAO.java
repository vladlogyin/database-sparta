package over.achievers.database.SQLServer;


import over.achievers.database.model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EmployeeDAO implements DAOInterface {
    private ConnectionFactory connectionFactory;

    public void saveFromCollection(List<Employee> employeeList){
        for (Employee emp : employeeList){
            saveEmployee(emp);
        }
    }
    @Override
    public void saveEmployee(Employee employee) {
        Connection conn = ConnectionFactory.getConnection();
        try { // emp_number,name_preference
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO employee (emp_number,name_preference,first_name,middle_name,last_name,gender,email,date_of_birth,joining_date,salary) " +
                    "VALUES (" + employee.getEmpNumber()
                    + ", " + "'" + employee.getNamePreference()
                    + "'" + ", " + "'" + employee.getFirstName()
                    + "'" + ", " + "'" + employee.getMiddleName()
                    + "'" + ", " + "'" + employee.getLastName()
                    + "'" + ", " + "'" + employee.getGender()
                    + "'" + ", " + "'" + employee.getEmail()
                    + "'" + ", " + "'" + employee.getDateOfBirth()
                    + "'" + ", " + "'" + employee.getJoiningDate()
                    + "'" + ", " + employee.getSalary() + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getEmployeeByID(Integer id) {
        Connection conn = ConnectionFactory.getConnection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM employee WHERE emp_number=%d", id));
            rs.next();
            StringBuffer sb = new StringBuffer();
            for (int i = 1; i < 11; i++) {
                sb.append(rs.getString(i) + " ");

            }
//            String result = rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getString(4) + rs.getString(5)
            System.out.println(sb.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
