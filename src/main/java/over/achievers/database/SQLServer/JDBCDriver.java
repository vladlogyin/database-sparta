package over.achievers.database.SQLServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// TODO create a "EmployeeDAO.java" interface

public class JDBCDriver {
        public static void main(String[] args) {
            try(            Connection conn = ConnectionFactory.getConnection()){
                Statement statement = conn.createStatement();
                statement.executeUpdate("insert into employee (emp_number,name_preference,first_name,middle_name,last_name,gender,email,date_of_birth,joining_date,salary)\n" +
                        "values(2,'mr','James','m','black','m','jonm@gmail.com','1988-12-12','2005-01-05',5000)");
                statement.
                ResultSet rs = statement.executeQuery("SELECT * FROM employee");

                while(rs.next()){
//                System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
                    System.out.println(rs.getString(1) + rs.getString(2) + " " + rs.getString(3));
                }

//            int rows_updated = statement.executeUpdate("INSERT INTO film_text()" + "VALUES(1001, 'eng125/148','Top developers')");
//            System.out.println(rows_updated);
//            conn.close();
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException();
            }
        }


//        private Statement dao;
//    JDBCDriver(){
//        try(Connection conn = ConnectionFactory.getConnection()){
//            Statement dao = conn.createStatement();
//        } catch (Exception e){
//            e.printStackTrace();
//            //throw new RuntimeException();
//        }
//    }
//
//    public static void createEmployee(Employee employee){
//        employee.
//        dao.execute
//    }

}
