package over.achievers.database.Controller;

import over.achievers.database.SQLServer.ConnectionFactory;
import over.achievers.database.SQLServer.EmployeeDAO;
import over.achievers.database.model.Employee;
import over.achievers.database.model.Logger;
import over.achievers.database.parsing.EmployeeImporter;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.*;
import over.achievers.database.viewer.MainViewer;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collection;

public class ControllerMain {
    private static EmployeeImporter employees;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
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
            employees = EmployeeImporter.fromCSV("src/main/resources/EmployeeRecords1.csv", validators);
        } catch (FileNotFoundException e) {
            MainViewer.printMessage("Could not get employee records");
            Logger.info("Problem getting employee records");
            throw new RuntimeException(e);
        }
        int threadCount = MainViewer.getThreadChoice();
        writeToDatabase(threadCount);

        getUsers();
    }

    static void writeToDatabase(int threads) {
        Collection list = employees.getValidEmployees();
        MainViewer.startMessage();
        boolean gotAccess = false;
        int tries = 0;
        while (!gotAccess) {
            try {
                new ConnectionFactory().getConnection();
                MainViewer.printMessage("connection successful\n");
                gotAccess = true;
            } catch (SQLException e) {
                try {
                    if (tries == 0)
                        ConnectionFactory.loadConfig("src/main/resources/database.properties");
                    else if (tries>0 && MainViewer.reloadProperties()){
                        ConnectionFactory.loadConfig("src/main/resources/database.properties");
                    } else{
                        System.exit(0);
                    }
                    tries++;
                } catch (FileNotFoundException ewwww) {
                    Logger.warn(ewwww.getMessage());
                    MainViewer.printMessage("Credentials not found");
                    if (MainViewer.userHasConfig()) {
                        String[] userCredentials = MainViewer.getUserCredentials();
                        ConnectionFactory.setConfig(userCredentials[0], userCredentials[1], userCredentials[2]);
                    } else
                        System.exit(0);
                }
            }
        }
        long startTime = System.nanoTime();
        try {
            EmployeeDAO.truncateTable();
            EmployeeDAO.saveFromCollectionMultithreadedSuperFast(list, threads);
        } catch (SQLException e) {
            Logger.info("Problem when writing to table: " + e.getMessage());
        }
        long endTime = System.nanoTime();
        MainViewer.printRunTime((endTime - startTime) / 1_000_000);
        int totalInvalid = employees.getInvalidEmployees().size() + employees.getInvalidLines().size();
        MainViewer.dataLoadedMessage(employees.getValidEmployees().size(), totalInvalid);
        if (totalInvalid > 0 && MainViewer.getViewChoice()) {
            MainViewer.showData(employees.getInvalidEmployees(), "\n---------- Records with invalid data entries: ---------- \n");
            MainViewer.showData(employees.getInvalidLines(), "\n---------- Records with invalid data formatting: ---------- \n");
        }
    }

    static void getUsers() {
        boolean stop = false;
        while (!stop) {
            int empId = MainViewer.getEmpId();
            try {
                Employee emp = EmployeeDAO.getEmployeeByID(empId);
                MainViewer.displayEmployee(emp);
            }
            catch (SQLException e) {

                MainViewer.printMessage("Could not get employee");
                Logger.warn("Could not get employee: " + e.getMessage());
            }
            if (!MainViewer.viewAgain()) {
                stop = true;
            }
        }
    }
}
