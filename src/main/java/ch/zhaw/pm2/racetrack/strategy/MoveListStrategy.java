package ch.zhaw.pm2.racetrack.strategy;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;

import java.util.ArrayList;
import java.util.List;

public class MoveListStrategy implements MoveStrategy {

    private List<Direction> directions;
    @Override
    public Direction nextMove() {
        // TODO: implementation

         directions = new ArrayList<>();
        throw new UnsupportedOperationException();
    }
}
