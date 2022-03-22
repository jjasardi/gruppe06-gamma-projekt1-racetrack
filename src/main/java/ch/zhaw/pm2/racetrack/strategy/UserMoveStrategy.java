package ch.zhaw.pm2.racetrack.strategy;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.ui.Input;

/**
 *  This class lets the user decide the next move.
 */
public class UserMoveStrategy implements MoveStrategy {

    private Input input;

    /**
     * Creates an object of class @{@link UserMoveStrategy}
     * @param input the current @{@link Input} object
     */
    public UserMoveStrategy(Input input) {
        this.input = input;
    }

    @Override
    public Direction nextMove() {
        return input.getUserMoveDirection();
    }
}
