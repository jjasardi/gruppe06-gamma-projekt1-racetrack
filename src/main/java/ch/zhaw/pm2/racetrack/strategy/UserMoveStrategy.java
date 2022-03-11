package ch.zhaw.pm2.racetrack.strategy;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.ui.Input;
import org.beryx.textio.TextIO;

/**
 * Let the user decide the next move.
 */
public class UserMoveStrategy implements MoveStrategy {

    private Input input;

    /**
     * @param input
     */
    public UserMoveStrategy(Input input) {
        this.input = input;
    }

    @Override
    public Direction nextMove() {
        return input.askUserMoveDirection();
    }
}
