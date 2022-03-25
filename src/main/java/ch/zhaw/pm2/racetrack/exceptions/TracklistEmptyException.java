package ch.zhaw.pm2.racetrack.exceptions;

/**
 * This exception class is used when the track list is empty.
 */
public class TracklistEmptyException extends Exception {

    private static final long serialVersionUID = 8333293512238364852L;

    /**
     * Creates an TracklistEmptyException object
     * @param errorMessage  String which contains the error message
     */
    public TracklistEmptyException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
