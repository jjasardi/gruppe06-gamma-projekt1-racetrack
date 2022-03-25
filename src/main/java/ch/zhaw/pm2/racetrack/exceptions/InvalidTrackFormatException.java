package ch.zhaw.pm2.racetrack.exceptions;

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

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
