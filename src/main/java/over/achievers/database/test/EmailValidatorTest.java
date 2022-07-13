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

}