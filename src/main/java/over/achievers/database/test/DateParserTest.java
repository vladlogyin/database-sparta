package over.achievers.database.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import over.achievers.database.parsing.DateParser;
import over.achievers.database.parsing.InvalidFormatException;

import java.sql.Date;
import java.time.LocalDate;

class DateParserTest {

    @Test
    @DisplayName("Parse valid date")
    void testParseValidDate(){
        Assertions.assertEquals(Date.valueOf(LocalDate.of(2021,1,1)), DateParser.parse("01/01/2021"));
    }

    @Test
    @DisplayName("Parse invalid day")
    void testParseInvalidDay(){
        Exception e = Assertions.assertThrows(InvalidFormatException.class, () -> DateParser.parse("01/55/2021"));
        Assertions.assertEquals("Invalid format exception: Issue with date format: Invalid value for DayOfMonth (valid values 1 - 28/31): 55", e.getMessage());
    }

    @Test
    @DisplayName("Parse invalid month")
    void testParseInvalidMonth(){
        Exception e = Assertions.assertThrows(InvalidFormatException.class, () -> DateParser.parse("15/01/2021"));
        Assertions.assertEquals("Invalid format exception: Issue with date format: Invalid value for MonthOfYear (valid values 1 - 12): 15", e.getMessage());
    }

    @Test
    @DisplayName("Parse invalid year")
    void testParseInvalidYear(){
        Exception e = Assertions.assertThrows(InvalidFormatException.class, () -> DateParser.parse("01/01/2021.5"));
        Assertions.assertEquals("Invalid format exception: Issue with date format: For input string: \"2021.5\"", e.getMessage());
    }

    @Test
    @DisplayName("Unparse date")
    void testUnparseDate(){
        Assertions.assertEquals("01/01/2021", DateParser.unparse(Date.valueOf(LocalDate.of(2021,1,1))));
    }

}