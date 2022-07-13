package over.achievers.database.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.Parser;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateValidatorTest {

    DateValidator dateValidator;
    Parser employeeParser;

    @BeforeEach
    void setUp() {
        dateValidator = new DateValidator();
        employeeParser = new Parser();
    }


    @Test
    @DisplayName("Join date after DoB")
    void testJoiningDateAfterDoB(){
        // DoB: 12/12/1974  Joining Date: 6/4/2011
        Employee employee = employeeParser.parse("1,Mr.,Tim,A,Duncan,M,timduncan@hotmail.com,12/12/1974,6/4/2011,26012");
        Assertions.assertEquals(true, dateValidator.isValid(employee));
    }

}