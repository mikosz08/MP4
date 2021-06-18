package models.exception;

public class DataValidationException extends RuntimeException {

    /**
     * Class constructor.
     */
    public DataValidationException() {
    }

    /**
     * Class constructor with custom message.
     */
    public DataValidationException(String message) {
        super(message);
    }

}
