package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.Track;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;
import ch.zhaw.pm2.racetrack.logic.Config;

import java.io.File;

/**
 * This class is for all the output of the game. It uses the TextIO library to
 * print the output in the console.
 */

public class Output {
    private final ConsoleInterface consoleInterface = new ConsoleInterface();

    /**
     * Prints the welcome text for the game
     */
    public void welcomeToRacetrack() {
        consoleInterface.printWelcomeText();
    }

    /**
     * Prints the appeal for the player to choose a @{@link Track}
     * @param trackDirectory    directory of the @{@link Track}
     * @throws TracklistEmptyException
     */
    public void outputTrackList(File trackDirectory) throws TracklistEmptyException {
        consoleInterface.printTrackList(trackDirectory);
    }

    /**
     * Executes the appeal for the player to choose a @{@link File} for
     * the @{@link ch.zhaw.pm2.racetrack.strategy.MoveListStrategy}
     * @param moveDirectory directory of the @{@link File}
     * @throws MoveListEmptyException
     */
    public void outputMoveList(File moveDirectory) throws MoveListEmptyException {
        consoleInterface.printMoveList(moveDirectory);
    }

    /**
     * Executes the print of all available @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     * @param strategyTypes array of all @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     */
    public void outputStrategyTypes(StrategyType[] strategyTypes) {
        consoleInterface.printStrategyTypes(strategyTypes);
    }

    /**
     * Executes the
     * @param moveDirections
     */
    public void outputNextMove(PositionVector.Direction[] moveDirections) {
        consoleInterface.printNextMoveList(moveDirections);
    }

    public void outputUserDialogFeatures(Config.DialogFeature dialogFeature){
        consoleInterface.printUserDialogFeatures();
    }

}
