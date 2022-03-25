package ch.zhaw.pm2.racetrack.exceptions;

/**
 * This exception class is used when the track format is invalid.
 */
public class InvalidTrackFormatException extends Exception {

    private static final long serialVersionUID = 762037463419578740L;

    /**
     * Creates an InvalidTrackFormatException object
     * @param errorMessage  String which contains the error message
     */
    public InvalidTrackFormatException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
