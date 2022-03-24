package ch.zhaw.pm2.racetrack.strategy;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;

/**
 * Do not accelerate in any direction.
 */
public class DoNotMoveStrategy implements MoveStrategy {

    @Override
    public Direction nextMove() {
        return Direction.NONE;
    }
}
