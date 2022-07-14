package Controller;

import over.achievers.database.SQLServer.EmployeeDAO;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.EmployeeImporter;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.*;
import over.achievers.database.viewer.MainViewer;

import java.io.FileNotFoundException;
import java.util.Collection;

public class ControllerMain {
    public static void start(){
        Parser par2 = new Parser();
        Employee employee = par2.parse("178566,Mrs.,Juliette,M,Rojo,F,juliette.rojo@yahoo.co.uk,5/8/1967,6/4/2011,193912");
        EmployeeDAO dao = new EmployeeDAO();

        Validator[] validators = new Validator[]{
                new IDValidator(),
                new DateValidator(),
                new DOBValidator(),
                new GenderValidator(),
                new SalaryValidator(),
                new EmailValidator()
        };
        EmployeeImporter employees = null;
        try {
            employees = EmployeeImporter.fromCSV("src/main/resources/EmployeeRecordsLarge.csv", validators);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Collection<Employee> list = employees.getValidEmployees();


    }

    void writeToDatabase(Collection list){
        System.out.println("Multi thread performance test ");
        long startTime = System.nanoTime();
        EmployeeDAO.truncateTable();
        EmployeeDAO.saveFromCollectionMultithreadedSuperFast(list, 12);
        long endTime = System.nanoTime();
        System.out.println("Operation took " + (endTime - startTime) /1_000_000 +" milliseconds");
        MainViewer.dataLoadedMessage(3, 5);
        int empId = MainViewer.getEmpId();
        System.out.println("id entered = " + empId);
        Employee emp = EmployeeDAO.getEmployeeByID(empId);
        System.out.println(emp);
    }
}
