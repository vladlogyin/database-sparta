package over.achievers.database.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.IDValidator;

import static org.junit.jupiter.api.Assertions.*;

class IDValidatorTest {

    IDValidator idValidator;
    Parser employeeParser;

    @BeforeEach
    void setUp() {
        idValidator = new IDValidator();
        employeeParser = new Parser();
    }

    @Test
    @DisplayName("Positive integer ID")
    void testPositiveIntegerID(){
        // ID: 199
        Employee employee = employeeParser.parse("199,Mr.,Khabib,A,Nurmagomedov,M,knurm77@hotmail.com,10/10/1982,4/1/2009,100123");
        Assertions.assertEquals(true, idValidator.isValid(employee));
    }

    @Test
    @DisplayName("Negative integer ID")
    void testNegativeIntegerID(){
        // ID: -500
        Employee employee = employeeParser.parse("-500,Mr.,Khabib,A,Nurmagomedov,M,knurm77@hotmail.com,10/10/1982,4/1/2009,100123");
        Assertions.assertEquals(false, idValidator.isValid(employee));
    }

    @Test
    @DisplayName("0 ID")
    void testZeroID(){
        // ID: 0
        Employee employee = employeeParser.parse("0,Mr.,Khabib,A,Nurmagomedov,M,knurm77@hotmail.com,10/10/1982,4/1/2009,100123");
        Assertions.assertEquals(true, idValidator.isValid(employee));
    }

    @Test
    @DisplayName("Duplicate ID")
    void testDuplicateID(){
        // ID: 1000
        Employee employee1 = employeeParser.parse("1000,Mr.,Khabib,A,Nurmagomedov,M,knurm77@hotmail.com,10/10/1982,4/1/2009,100123");
        Employee employee2 = employeeParser.parse("1000,Mr.,Conor,D,McGregor,M,c_mc_gregor@hotmail.com,1/12/1980,8/4/2004,90019");
        Assertions.assertEquals(true, idValidator.isValid(employee1));
        Assertions.assertEquals(false, idValidator.isValid(employee2));
    }

}