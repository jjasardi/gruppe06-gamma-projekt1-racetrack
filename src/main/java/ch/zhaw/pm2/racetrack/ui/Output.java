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
     *
     */
    public void welcomeToRacetrack() {
        consoleInterface.printWelcomeText();
    }

    /**
     * @param trackDirectory
     * @throws TracklistEmptyException
     */
    public void outputTrackList(File trackDirectory) throws TracklistEmptyException {
        consoleInterface.printTrackList(trackDirectory);
    }

    /**
     * @param moveDirectory
     * @throws MoveListEmptyException
     */
    public void outputMoveList(File moveDirectory) throws MoveListEmptyException {
        consoleInterface.printMoveList(moveDirectory);
    }

    /**
     *
     * @param strategyTypes
     */
    public void outputStrategyTypes(StrategyType[] strategyTypes) {
        consoleInterface.printStrategyTypes(strategyTypes);
    }

    /**
     * @param moveDirections
     */
    public void outputNextMove(PositionVector.Direction[] moveDirections) {
        consoleInterface.printNextMoveList(moveDirections);
    }

    public void outputUserDialogFeatures(Config.DialogFeature dialogFeature){
        consoleInterface.printUserDialogFeatures();
    }

    public void outputWinner(char carID){
        consoleInterface.printWinnerText(carID);
    }
}
