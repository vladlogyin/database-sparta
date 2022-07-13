package over.achievers.database.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.DateValidator;

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

    @Test
    @DisplayName("Join date before DoB")
    void testJoiningDateBeforeDoB(){
        // DoB: 5/11/2019   Joining Date: 3/8/2005
        Employee employee = employeeParser.parse("2,Mr.,Jason,L,Kidd,M,jkidd6@gmail.com,5/11/2019,3/8/2005,489101");
        Assertions.assertEquals(false, dateValidator.isValid(employee));
    }

    @Test
    @DisplayName("Join date before future")
    void testJoiningDateBeforeFuture(){
        // Joining Date: 1/25/1988
        Employee employee = employeeParser.parse("3,Mr.,Nikola,O,Jokic,M,nikjok6892@gmail.com,7/28/1960,1/25/1988,20918");
        Assertions.assertEquals(true, dateValidator.isValid(employee));
    }

    @Test
    @DisplayName("Join date in future")
    void testJoiningDateInFuture(){
        // Joining Date: 11/9/2048
        Employee employee = employeeParser.parse("4,Ms.,Dana,Z,White,F,dwh1te34@msn.com,2/18/1998,11/9/2048,378162");
        Assertions.assertEquals(false, dateValidator.isValid(employee));
    }

}