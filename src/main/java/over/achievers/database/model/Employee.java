package over.achievers.database.model;
import over.achievers.database.parsing.DateParser;
import over.achievers.database.validation.Validator;

import java.sql.Date;

public class Employee {

    private int empNumber;
    private String namePreference;
    private String firstName;
    private char middleName;
    private String lastName;
    private char gender;
    private String email;
    private Date dateOfBirth;
    private Date joiningDate;
    private int salary;

    public Employee(int empNumber, String namePreference, String firstName, char middleName, String lastName, char gender, String email, Date dateOfBirth, Date joiningDate, int salary) {
        this.empNumber = empNumber;
        this.namePreference = namePreference;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.joiningDate = joiningDate;
        this.salary = salary;
    }
    public Employee()
    {
        empNumber=0;
        namePreference="";
        firstName="";
        middleName=' ';
        lastName="";
        gender=' ';
        email="";
        dateOfBirth=new Date(0);
        joiningDate=new Date(0);
        salary=0;
    }
    public boolean isValid(Validator[] validators)
    {
        for(Validator v : validators)
        {
            if(!v.isValid(this))
            {
                return false;
            }
        }
        return true;
    }

    public int getEmpNumber() {
        return empNumber;
    }

    public Employee setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
        return this;
    }

    public String getNamePreference() {
        return namePreference;
    }

    public Employee setNamePreference(String namePreference) {
        this.namePreference = namePreference;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public char getMiddleName() {
        return middleName;
    }

    public Employee setMiddleName(char middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public char getGender() {
        return gender;
    }

    public Employee setGender(char gender) {
        this.gender = gender;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Employee setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public Employee setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
        return this;
    }

    public int getSalary() {
        return salary;
    }

    public Employee setSalary(int salary) {
        this.salary = salary;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(200);
        sb.append(empNumber);
        sb.append(',');
        sb.append(namePreference);
        sb.append(',');
        sb.append(firstName);
        sb.append(',');
        sb.append(middleName);
        sb.append(',');
        sb.append(lastName);
        sb.append(',');
        sb.append(gender);
        sb.append(',');
        sb.append(email);
        sb.append(',');
        sb.append(DateParser.unparse(dateOfBirth));
        sb.append(',');
        sb.append(DateParser.unparse(joiningDate));
        sb.append(',');
        sb.append(salary);
        return sb.toString();
    }
}
