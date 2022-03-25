package ch.zhaw.pm2.racetrack.exceptions;

/**
 * This exception class is used when the file format is invalid.
 */
public class InvalidFileFormatException extends Exception {

    private static final long serialVersionUID = 7421314400704183413L;

    /**
     * Creates an InvalidFileFormatException object
     * @param errorMessage  String which contains the error message
     */
    InvalidFileFormatException(String errorMessage) {
        super(errorMessage);
    }
}
