package over.achievers.database.parsing;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static Date parse(String strDate) throws InvalidFormatException, NullPointerException
    {
        try {
            return Date.valueOf(LocalDate.parse(strDate,dateFormat));
        }
        catch( DateTimeParseException e)
        {
            throw new InvalidFormatException("Issue with date format: "+e.getMessage());
        }

    }
}
