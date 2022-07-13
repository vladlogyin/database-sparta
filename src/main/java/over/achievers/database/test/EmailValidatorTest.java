package over.achievers.database.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.DOBValidator;
import over.achievers.database.validation.EmailValidator;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    EmailValidator emailValidator;

    Parser employeeParser;

    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidator();
        employeeParser = new Parser();
    }

    @Test
    @DisplayName("Standard email")
    void testStandardEmail(){
        // Email: random123@hotmail.com
        Employee employee = employeeParser.parse("1,Mrs.,Jane,P,Giddey,F,random123@hotmail.com,2/15/2001,11/3/2021,350921");
        Assertions.assertEquals(true, emailValidator.isValid(employee));
    }

    @Test
    @DisplayName("Numerical email")
    void testNumericalEmail(){
        // Email: 12345
        Employee employee = employeeParser.parse("2,Mr.,John,A,Wall,M,12345,2/11/1998,2/16/2015,103912");
        Assertions.assertEquals(false, emailValidator.isValid(employee));
    }

    @Test
    @DisplayName("Symbolic email")
    void testSymbolicEmail(){
        // Email: 12345
        Employee employee = employeeParser.parse("2,Mr.,Russel,D,Westbrook,M,~@@~:}{:@,12/21/976,9/11/2012,909010");
        Assertions.assertEquals(false, emailValidator.isValid(employee));
    }

}