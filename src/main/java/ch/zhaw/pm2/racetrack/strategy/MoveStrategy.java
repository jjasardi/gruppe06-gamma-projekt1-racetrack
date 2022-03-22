package ch.zhaw.pm2.racetrack.strategy;

import static ch.zhaw.pm2.racetrack.PositionVector.Direction;

public interface MoveStrategy {
    /**
     * Performs the next move of the car due to selections of the player
     * @return @{@link Direction} of the new acceleration of the car @{@link ch.zhaw.pm2.racetrack.Car}
     */
    Direction nextMove();
}
