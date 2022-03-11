package ch.zhaw.pm2.racetrack.strategy;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.logic.Config;
import ch.zhaw.pm2.racetrack.ui.Input;
import ch.zhaw.pm2.racetrack.ui.Output;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MoveListStrategy implements MoveStrategy {
    private final Config config;
    private final Output output;
    private final Input input;

    private List<Direction> movesList;
    private int currentMoveIndex;

    public MoveListStrategy(Input input, Output output, Config config) {
        this.config = config;
        this.output = output;
        this.input = input;
        movesList = new ArrayList<>();
        currentMoveIndex = 0;

        File moveFile = selectMoveListFile();
        readAllMoves(moveFile);
    }

    private File selectMoveListFile() {
        File moveDirectory = config.getMoveDirectory();
        output.outputMoveList(moveDirectory);
        return input.getSelectedMoveFile(moveDirectory);
    }

    private void readAllMoves(File moveFile) {
        try {
            Scanner scanner = new Scanner(moveFile);
            while (scanner.hasNext()) {
                Direction direction = Direction.valueOf(scanner.nextLine());
                movesList.add(direction);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Direction nextMove() {
        if (currentMoveIndex < movesList.size()){
            Direction currentMove = movesList.get(currentMoveIndex);
            ++currentMoveIndex;
            return currentMove;
        } else{
            return Direction.NONE;
        }
    }
}
