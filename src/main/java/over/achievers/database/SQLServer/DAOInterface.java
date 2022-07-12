package over.achievers.database.SQLServer;

import over.achievers.database.model.Employee;

/*
C - create
R -read
U -update
D - delete
one method to search by emp_number
 */

public interface DAOInterface {

    void saveEmployee(Employee employee);

    void getEmployeeByID(Integer id);
}
