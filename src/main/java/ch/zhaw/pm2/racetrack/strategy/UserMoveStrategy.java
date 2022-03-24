package ch.zhaw.pm2.racetrack.strategy;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.ui.Input;
import ch.zhaw.pm2.racetrack.ui.Output;

/**
 * Let the user decide the next move.
 */
public class UserMoveStrategy implements MoveStrategy {

    private Input input;
    private Output output;

    /**
     * @param input
     */
    public UserMoveStrategy(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public Direction nextMove() {
        output.outputDirectionAppeal();
        return input.getChoosedDirection();
    }
}
