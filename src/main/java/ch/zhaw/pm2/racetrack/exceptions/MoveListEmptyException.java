package ch.zhaw.pm2.racetrack.exceptions;

/**
 * This exception class is used when the move list is empty.
 */
public class MoveListEmptyException extends Exception {

    private static final long serialVersionUID = 1871145064442655706L;

    /**
     * Creates an MoveListEmptyException object
     * @param errorMessage  String which contains the error message
     */
    public MoveListEmptyException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
