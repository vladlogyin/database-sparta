package over.achievers.database.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.DateParser;
import over.achievers.database.parsing.InvalidFormatException;
import over.achievers.database.parsing.Parser;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    Parser employeeParser;

    @BeforeEach
    void setUp() {
        employeeParser = new Parser();
    }

    @Test
    @DisplayName("Parse valid employee")
    void testParseValidEmployee(){
        Employee employee = new Employee(1, "Mrs.", "Ronda", 'W', "Jackson", 'F', "rjackson77@hotmail.com", DateParser.parse("10/10/1982"), DateParser.parse("4/1/2009"), 100123);
        Assertions.assertEquals(employee.toString(), employeeParser.parse("1,Mrs.,Ronda,W,Jackson,F,rjackson77@hotmail.com,10/10/1982,4/1/2009,100123").toString());
    }

    @Test
    @DisplayName("Parse null")
    void testParseNull(){
        // Throws a NullPointerException
        Assertions.assertThrows(NullPointerException.class, () -> employeeParser.parse(null));
    }

    @Test
    @DisplayName("Parse empty")
    void testParseEmpty(){
        // Throws an InvalidFormatException
        Exception e = Assertions.assertThrows(InvalidFormatException.class, () -> employeeParser.parse(""));
        Assertions.assertEquals("Invalid format exception: token index(0-based):1\nFor input string: \"\"", e.getMessage());
    }

}