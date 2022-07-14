# Employee CSV Data Migration Project

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Screenshots](#screenshots)
* [Setup](#setup)
* [MySQL](#mysql-setup)
* [Performance upgrades](#Performance upgrades)
* [Contact](#contact)


## General Information
- Application created as part of sparta global training
- App allow to migrate data form ```CSV``` file onto database (MySql)
- Command Line Interface

## Technologies Used

- [SOLID](https://en.wikipedia.org/wiki/SOLID)
- [OOP](https://en.wikipedia.org/wiki/Object-oriented_programming)
- [MVC](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
- [MySQL](https://en.wikipedia.org/wiki/MySQL)
- [MAVEN](https://en.wikipedia.org/wiki/Apache_Maven)
- [GIT](https://en.wikipedia.org/wiki/Git)/[GITHUB](https://en.wikipedia.org/wiki/GitHub)
- [Design patterns](https://en.wikipedia.org/wiki/Software_design_pattern):
    - [Builder](https://en.wikipedia.org/wiki/Builder_pattern)
    - [Singleton](https://en.wikipedia.org/wiki/Singleton_pattern)
    - [DAO](https://en.wikipedia.org/wiki/Data_access_object)
    - [Decorator](https://en.wikipedia.org/wiki/Decorator_pattern)
- [Collections](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/Collections.html)
- [jUnit](https://en.wikipedia.org/wiki/JUnit)
- [Log4j](https://en.wikipedia.org/wiki/Log4j)
- [JDBC](https://en.wikipedia.org/wiki/Java_Database_Connectivity)
- [Exceptions](https://docs.oracle.com/javase/7/docs/api/java/lang/Exception.html)
- [Scanner](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/util/Scanner.html)
- [Multi-Threading](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/Thread.html)

## Features


## Screenshots


## Setup
In order to use this application please clone repository onto your local machine
1. Use this [link](https://github.com/vladlogyin/database-sparta) (click on icon)<br>
2. Open your IntelliJ (or any other IDE of your preference )<br>
3. Create New > Project from version control<br>
   ![creting new project](/src/main/resources/new%20project.png)<br> 
4. Paste repo ([link](https://github.com/vladlogyin/database-sparta)) & select where project will be saved. Click clone on bottom.

5. Once project is opened, load Maven build (pop up in right-hand corner)
   ![maven_build](/src/main/resources/Maven.PNG)
6. Create new file under path:
>src/main/resources/

and name file -> **database.properties**

6. Inside please update your database credentials (where XXX is your login & password )
> db.url = jdbc:mysql://localhost:3306/employee<br>
> db.username =XXX<br>
> db.password = XXX<br>
7. Move to SQL Setup

## MySQL setup

1. This software requires MySQL & MySQL workbench(or any other tool of your preference )
2. Create new schema (copy script)
```sql
CREATE schema employee;
```
3. Create new tables (copy script)
```sql
DROP TABLE IF exists employee;
CREATE table employee (
emp_number integer primary KEY,
name_preference varchar(5),
first_name varchar(20),
middle_name VARCHAR(1),
last_name varchar(20),
gender char,
email VARCHAR(40),
date_of_birth date,
joining_date date,
salary integer
);
```

## Performance upgrades

Our base case for checking performance was saving each employee in database using:
>[Prepared Statement](https://docs.oracle.com/en/java/javase/18/docs/api/java.sql/java/sql/PreparedStatement.html) with [auto-commit](https://docs.oracle.com/en/java/javase/18/docs/api/java.sql/java/sql/Connection.html#setAutoCommit(boolean)) - **true**

```java
public static void saveEmployee(Employee employee, PreparedStatement statement) {
        Connection conn = ConnectionFactory.getConnection();
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
            Logger.error(e.getMessage());
        }
    }
```
```java
public static void saveFromCollection(Collection<Employee> employeeList, Boolean autoCommit) {
        try {
            ConnectionFactory.getConnection().setAutoCommit(autoCommit);
        } catch (SQLException e) {
            Logger.error(e.getMessage());
        }
        for (Employee emp : employeeList) {
            saveEmployee(emp);
        }
        try {
            ConnectionFactory.connection.commit();
        } catch (SQLException e) {
            Logger.error(e.getMessage());
        }
    }
```

![stage1 resluts](src/main/resources/stage1.PNG)

Our next step was to set [auto-commit](https://docs.oracle.com/en/java/javase/18/docs/api/java.sql/java/sql/Connection.html#setAutoCommit(boolean)) to **false**.<br><br>
![stage1 resluts](src/main/resources/stage2.PNG)

At this stage we discover that committing changes after whole batch is ready is more efficient than committing changes after each individual employee. <br>

Knowing all of that, we tested if multi-threading will increase performance even further

```java
public static void saveFromCollectionParallel(Collection<Employee> employeeList) {
        final int threadCount = 2;

        long startNano = System.nanoTime();
        final Connection conn = ConnectionFactory.getConnection();
        final PreparedStatement[] threadSpecificStatements = new PreparedStatement[threadCount];
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < threadCount; i++) {
                threadSpecificStatements[i] = conn.prepareStatement(INSERT_SQL_STATEMENT);
            }
        } catch (SQLException e) {
            Logger.error("Exception thrown during statement setup:\n" + e.toString());
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
                    Logger.error("Exception thrown in thread "+threadID+" during statement execution:\n" + e.toString());
                }
            }, threadCount);

        }
        catch (RuntimeException e)
        {
            Logger.error("Exception thrown during statement execution:\n" + e.toString());
        }

        long nanoDiff = System.nanoTime()-startNano;
        double milliSeconds = nanoDiff/1E6;
        System.out.println("Threads: " + threadCount + " time spent:" + milliSeconds+"ms");
    }
```

![multiThreadsResults](src/main/resources/MultiThread.PNG)

**At this stage we reduce inserting time from 3.5 minutes down to around 14 sek.**

>*Our next goal was try to go down below 10 sek for whole operation.*

We slightly changed our approach to this. We decided to test if building SQL statement would be more efficient if we use [StringBuilder](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/StringBuilder.html).

```java
public static void saveFromCollectionParallelSuperFast(Collection<Employee> employeeList)
    {
        final int threadCount=2;

        long startNano = System.nanoTime();
        Connection conn = ConnectionFactory.getConnection();
        final PreparedStatement[] threadSpecificStatements = new PreparedStatement[threadCount];
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < threadCount; i++) {
                threadSpecificStatements[i] = conn.prepareStatement(INSERT_SQL_STATEMENT);
            }
        } catch (SQLException e) {
            Logger.error("Exception thrown during statement setup:\n" + e.toString());
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
            throw new RuntimeException(e);
        }

        long nanoDiff = System.nanoTime()-startNano;
        double milliSeconds = nanoDiff/1E6;
        System.out.println("Time spent:" + milliSeconds+"ms");
    }
```
<br>

![StringBuilderResults](src/main/resources/sbSigleThread.PNG)

Last test we performed was to check SB approaches with multi-threading

![StringBuilderResults](src/main/resources/sbMultiTrhead.PNG)

**All the test performed were based on:**
- same PC.
- identical data sets (CSV file).
- database tables were truncated before each test.


## Contact

Software created by : 

- [Mustafa Nawaz](	https://github.com/Typist01)
- [Bart Perczynski](https://github.com/Baaartosz)
- [Vlad Logyin](https://github.com/vladlogyin)
- [Omar Tehami](https://github.com/OTDZ)
- [Piotr(Peter) Wiatr](https://github.com/wiater88)


