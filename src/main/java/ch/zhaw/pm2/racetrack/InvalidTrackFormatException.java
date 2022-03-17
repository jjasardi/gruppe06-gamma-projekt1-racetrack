package ch.zhaw.pm2.racetrack;

/**
 *
 */
public class InvalidTrackFormatException extends Exception {

    private static final long serialVersionUID = 762037463419578740L;

    /**
     * @param errorMessage
     */
    public InvalidTrackFormatException(String errorMessage) {
        super(errorMessage);
    }
}
