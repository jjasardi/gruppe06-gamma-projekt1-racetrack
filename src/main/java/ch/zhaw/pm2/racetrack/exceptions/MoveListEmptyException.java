package ch.zhaw.pm2.racetrack.exceptions;

/**
 *
 */
public class MoveListEmptyException extends Exception {
    private static final long serialVersionUID = 1871145064442655706L;

    /**
     * @param errorMessage
     */
    public MoveListEmptyException(String errorMessage) {
        super(errorMessage);
    }
}
