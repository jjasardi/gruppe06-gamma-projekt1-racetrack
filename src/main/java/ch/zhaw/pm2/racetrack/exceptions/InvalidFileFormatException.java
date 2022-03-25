package ch.zhaw.pm2.racetrack.exceptions;


/**
 * 
 */
public class InvalidFileFormatException extends Exception {

    private static final long serialVersionUID = 7421314400704183413L;

    /**
     * @param errorMessage
     */
    InvalidFileFormatException(String errorMessage) {
        super(errorMessage);
    }
}
