package ch.zhaw.pm2.racetrack;

import java.io.Serial;

public class InvalidTrackFormatException extends Exception {
    @Serial
    private static final long serialVersionUID = 762037463419578740L;

    /**
     * @param errorMessage
     */
    public InvalidTrackFormatException(String errorMessage) {
        super(errorMessage);
    }
}
