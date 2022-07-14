package over.achievers.database.Controller;

import over.achievers.database.SQLServer.EmployeeDAO;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.EmployeeImporter;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.*;
import over.achievers.database.viewer.MainViewer;

import java.io.FileNotFoundException;
import java.util.Collection;

public class ControllerMain {
    private static EmployeeImporter employees;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
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
        employees = null;
        try {
            employees = EmployeeImporter.fromCSV("src/main/resources/EmployeeRecordsLarge.csv", validators);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int threadCount = MainViewer.getThreadChoice();
        writeToDatabase(threadCount);
        getUsers();
    }

    static void writeToDatabase(int threads) {
        Collection list = employees.getValidEmployees();
        MainViewer.startMessage();
        long startTime = System.nanoTime();
        EmployeeDAO.truncateTable();
        EmployeeDAO.saveFromCollectionMultithreadedSuperFast(list, threads);
        long endTime = System.nanoTime();
        MainViewer.printRunTime((endTime - startTime) / 1_000_000);
        int totalInvalid = employees.getInvalidEmployees().size() + employees.getInvalidLines().size();
        MainViewer.dataLoadedMessage(employees.getValidEmployees().size(), totalInvalid);
        if (totalInvalid > 0 && MainViewer.getViewChoice()) {
            MainViewer.showData(employees.getInvalidEmployees(), "Records with invalid data entries: ");
            MainViewer.showData(employees.getInvalidLines(), "Records with invalid data formatting: ");
        }
    }

    static void getUsers() {
        boolean stop = false;
        while (!stop) {
            int empId = MainViewer.getEmpId();
            Employee emp = EmployeeDAO.getEmployeeByID(empId);
            if (emp == null) {
                MainViewer.printMessage("Employee not found.");
            }
            MainViewer.displayEmployee(EmployeeDAO.getEmployeeByID(empId));
            if (!MainViewer.viewAgain()) {
                stop = true;
            }
        }
    }
}
