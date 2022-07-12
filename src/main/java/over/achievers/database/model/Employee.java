package over.achievers.database.model;
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
    //TODO is valid method to be implemented
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

    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    public String getNamePreference() {
        return namePreference;
    }

    public void setNamePreference(String namePreference) {
        this.namePreference = namePreference;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public char getMiddleName() {
        return middleName;
    }

    public void setMiddleName(char middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
