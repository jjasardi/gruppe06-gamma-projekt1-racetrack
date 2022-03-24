package ch.zhaw.pm2.racetrack.strategy;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.ui.Input;
import ch.zhaw.pm2.racetrack.ui.Output;

/**
 *  This class lets the user decide the next move.
 */
public class UserMoveStrategy implements MoveStrategy {

    private Input input;
    private Output output;

    /**
     * Creates an object of class @{@link UserMoveStrategy}
     * @param input the current @{@link Input} object
     */
    public UserMoveStrategy(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public Direction nextMove() {
        Direction[] moveDirections = Direction.values();
        output.outputNextMove(moveDirections);
        return input.getUserMoveDirection();
    }
}
