package over.achievers.database.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.DOBValidator;

import static org.junit.jupiter.api.Assertions.*;

class DOBValidatorTest {

    DOBValidator dobValidator;

    Parser employeeParser;

    @BeforeEach
    void setUp() {
        dobValidator = new DOBValidator();
        employeeParser = new Parser();
    }

    @Test
    @DisplayName("DoB before future")
    void testDoBBeforeFuture(){
        // DoB: 7/11/1999
        Employee employee = employeeParser.parse("1,Mr.,Nate,P,Archibald,M,narch123@hotmail.com,7/11/1999,1/1/2008,190123");
        Assertions.assertEquals(true, dobValidator.isValid(employee));
    }

}