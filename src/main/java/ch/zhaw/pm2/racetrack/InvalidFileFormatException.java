package ch.zhaw.pm2.racetrack;


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
