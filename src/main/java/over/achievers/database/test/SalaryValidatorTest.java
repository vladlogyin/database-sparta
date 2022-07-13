package over.achievers.database.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.SalaryValidator;

import static org.junit.jupiter.api.Assertions.*;

class SalaryValidatorTest {

    SalaryValidator salaryValidator;
    Parser employeeParser;

    @BeforeEach
    void setUp() {
        salaryValidator = new SalaryValidator();
        employeeParser = new Parser();
    }

    @Test
    @DisplayName("Positive integer salary")
    void testPositiveIntegerSalary(){
        Employee employee = employeeParser.parse("1,Mrs.,Ronda,W,Jackson,F,rjackson77@hotmail.com,10/10/1982,4/1/2009,100123");
        Assertions.assertEquals(true, salaryValidator.isValid(employee));
    }

    @Test
    @DisplayName("Negative integer salary")
    void testNegativeIntegerSalary(){
        Employee employee = employeeParser.parse("1,Mrs.,Ronda,W,Jackson,F,rjackson77@hotmail.com,10/10/1982,4/1/2009,-100123");
        Assertions.assertEquals(false, salaryValidator.isValid(employee));
    }

}