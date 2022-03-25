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

/**
 * This class reads the next moves frome a file.
 */
public class MoveListStrategy implements MoveStrategy {
    private final Config config;
    private final Output output;
    private final Input input;

    private List<Direction> movesList;
    private int currentMoveIndex;

    /**
     * Creates an object of the class {@link MoveListStrategy}
     * @param input object of the acctual input {@link Input}
     * @param output object of the accutal output {@link Output}
     * @param config object of the accutal config {@link Config}
     */
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

    /**
     * Reads out the different moves in the given {@link File} and puts them in {@link MoveListStrategy#movesList}
     * @param moveFile  file which contains the moves of the {@link ch.zhaw.pm2.racetrack.Car}
     */
    private void readAllMoves(File moveFile) {
        try {
            Scanner scanner = new Scanner(moveFile);
            while (scanner.hasNext()) {
                Direction direction = Direction.valueOf(scanner.nextLine());
                movesList.add(direction);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.err.println(exception.getMessage());
            System.exit(0);
        }
    }

    @Override
    public Direction nextMove() {
        if (currentMoveIndex < movesList.size()) {
            Direction currentMove = movesList.get(currentMoveIndex);
            ++currentMoveIndex;
            return currentMove;
        } else {
            return Direction.NONE;
        }
    }
}
