package ch.zhaw.pm2.racetrack.strategy;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.logic.Config;
import ch.zhaw.pm2.racetrack.ui.Input;
import ch.zhaw.pm2.racetrack.ui.Output;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MoveListStrategy implements MoveStrategy {
    private final Config config;
    private final Output output;
    private final Input input;
    private final File moveFile;

    private List<Direction> directions;

    public MoveListStrategy(){
        config = new Config();
        moveFile = selectMoveListFile();
        readAllDirections(moveFile);
    }

    private File selectMoveListFile(){
        File moveDirectory = config.getMoveDirectory();
        output.outputMoveList(moveDirectory);
        input.getSelectedMoveFile(moveDirectory);
    }

    private void readAllDirections(){
        
    }

    @Override
    public Direction nextMove() {
        // TODO: implementation

         directions = new ArrayList<>();
        throw new UnsupportedOperationException();
    }
}
