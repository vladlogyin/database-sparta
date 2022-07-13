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
        Employee employee = employeeParser.parse("1,Mr.,Mike,B,Victor,M,mikevic@gmail.com,7/12/1954,1/11/1978,346138");
        Assertions.assertEquals(true, genderValidator.isValid(employee));
    }

}