package over.achievers.database.parsing;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static Date parse(String strDate) throws InvalidFormatException, NullPointerException
    {
        try {
            String[] tokens = strDate.split("/");
            int month = Integer.parseInt(tokens[0]);
            int day = Integer.parseInt(tokens[1]);
            int year = Integer.parseInt(tokens[2]);
            return Date.valueOf(LocalDate.of(year,month,day));
        }
        catch( NumberFormatException e)
        {
            throw new InvalidFormatException("Issue with date format: "+e.getMessage());
        }
        catch (DateTimeException e)
        {
            throw new InvalidFormatException("Issue with date format: "+e.getMessage());
        }

    }
}
