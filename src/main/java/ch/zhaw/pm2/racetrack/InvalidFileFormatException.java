package ch.zhaw.pm2.racetrack;

import java.io.Serial;

/**
 * 
 */
public class InvalidFileFormatException extends Exception {
    @Serial
    private static final long serialVersionUID = 7421314400704183413L;

    /**
     * @param errorMessage
     */
    InvalidFileFormatException(String errorMessage) {
        super(errorMessage);
    }
}
