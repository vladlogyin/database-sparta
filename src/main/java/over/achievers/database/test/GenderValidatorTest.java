package over.achievers.database.test;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import over.achievers.database.model.Employee;
import over.achievers.database.parsing.Parser;
import over.achievers.database.validation.GenderValidator;

import static org.junit.jupiter.api.Assertions.*;

class GenderValidatorTest {

    GenderValidator genderValidator;
    Parser employeeParser;

    @BeforeEach
    void setUp() {
        genderValidator = new GenderValidator();
        employeeParser = new Parser();
    }

    @Test
    @DisplayName("Male")
    void testMaleGender(){
        // Gender: M
        Employee employee = employeeParser.parse("1,Mr.,Mike,B,Victor,M,mikevic@gmail.com,7/12/1954,1/11/1978,346138");
        Assertions.assertEquals(true, genderValidator.isValid(employee));
    }

    @Test
    @DisplayName("Female")
    void testFemaleGender(){
        // Gender: F
        Employee employee = employeeParser.parse("2,Mrs.,Ronda,W,Jackson,F,rjackson77@hotmail.com,10/10/1982,4/1/2009,100123");
        Assertions.assertEquals(true, genderValidator.isValid(employee));
    }

    @Test
    @DisplayName("Invalid letter")
    void testInvalidLetterGender(){
        // Gender: X
        Employee employee = employeeParser.parse("2,Mrs.,Ronda,W,Jackson,X,rjackson77@hotmail.com,10/10/1982,4/1/2009,100123");
        Assertions.assertEquals(false, genderValidator.isValid(employee));
    }

    @Test
    @DisplayName("Number")
    void testNumberGender(){
        // Gender: 9
        Employee employee = employeeParser.parse("2,Mrs.,Ronda,W,Jackson,9,rjackson77@hotmail.com,10/10/1982,4/1/2009,100123");
        Assertions.assertEquals(false, genderValidator.isValid(employee));
    }

    @Test
    @DisplayName("Symbol")
    void testSymbolGender(){
        // Gender: #
        Employee employee = employeeParser.parse("2,Mrs.,Ronda,W,Jackson,#,rjackson77@hotmail.com,10/10/1982,4/1/2009,100123");
        Assertions.assertEquals(false, genderValidator.isValid(employee));
    }

}