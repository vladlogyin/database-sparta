package over.achievers.database;

import over.achievers.database.SQLServer.EmployeeDAO;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.EmployeeImporter;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
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
        var employees = EmployeeImporter.fromCSV("src/main/resources/EmployeeRecordsLarge.csv", validators);
        Collection<Employee> list = employees.getValidEmployees();
        System.out.println("Multi thread performance test ");
        long startTime = System.nanoTime();
        EmployeeDAO.truncateTable();
        EmployeeDAO.saveFromCollectionParallelSuperFast(list);
        long endTime = System.nanoTime();
        System.out.println("Operation took " + (endTime - startTime) /1_000_000 +" milliseconds");

//
//        var employees2 = EmployeeImporter.fromCSV("src/main/Resource/EmployeeRecords1.csv", validators);
//        Collection<Employee> list2 = employees.getValidEmployees();
//        System.out.println("Single thread performance test ");
//        long startTime1 = System.nanoTime();
//        dao.saveFromCollection((List<Employee>) list2);
//        long endTime1 = System.nanoTime();
//        System.out.println("Operation took " + (endTime1 - startTime1) /1_000_000 +" milliseconds");


    }
}