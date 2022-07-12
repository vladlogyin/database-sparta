package over.achievers.database.parsing;

public class InvalidFormatException extends RuntimeException{
    public InvalidFormatException(String message)
    {
        super("Invalid format exception: " + message);
    }
}
