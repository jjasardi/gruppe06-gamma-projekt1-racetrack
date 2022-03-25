package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;

import java.io.File;

/**
 * This class is for all the output of the game. It uses the TextIO library to
 * print the output in the console.
 */

public class Output {
    private final ConsoleView consoleView = new ConsoleView();

    /**
     * Prints the welcome text for the game
     */
    public void outputWelcomeText() {
        consoleView.printWelcomeText();
    }

    /**
     * Prints the appeal for the player to choose a {@link ch.zhaw.pm2.racetrack.Track}
     *
     * @param trackDirectory directory of the {@link ch.zhaw.pm2.racetrack.Track}
     */
    public void outputTrackList(File trackDirectory) {
        try {
            consoleView.printTrackList(trackDirectory);
        } catch (TracklistEmptyException exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * Executes the print of all available {@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     *
     * @param strategyTypes array of all {@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     */
    public void outputStrategyTypes(StrategyType[] strategyTypes) {
        consoleView.printStrategyTypes(strategyTypes);
    }

    /**
     * This method calls the print move list method
     *
     * @param moveDirectory directory where the move files are
     */
    public void outputMoveList(File moveDirectory) {
        try {
            consoleView.printMoveList(moveDirectory);
        } catch (MoveListEmptyException exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * This method calls the method for the printing the game state.
     *
     * @param track current {@link ch.zhaw.pm2.racetrack.Track} object
     */
    public void outputGameState(String track) {
        consoleView.printGameState(track);
    }

    /**
     * This method executes the id of the current car
     *
     * @param carID char representig car ID
     */
    public void outputCurrentCarID(char carID) {
        consoleView.printCurrentCarID(carID);
    }

    /**
     * Executes the print of all possibles @{@link ch.zhaw.pm2.racetrack.PositionVector.Direction}
     */
    public void outputNextCommand() {
        consoleView.printNextCommand();
    }

    /**
     * Executes the print of the direction appeal.
     */
    public void outputDirectionAppeal() {
        consoleView.printDirectionAppeal();
    }

    /**
     * Executes the print of the user dialog features.
     */
    public void outputUserDialogFeatures() {
        consoleView.printUserDialogFeatures();
    }

    /**
     * Excutes the print of the winner text.
     *
     * @param carID char representing carID
     */
    public void outputWinnerText(char carID) {
        consoleView.printWinnerText(carID);
    }

    /**
     * Executes the print of the error message of {@link ch.zhaw.pm2.racetrack.exceptions.InvalidTrackFormatException}.
     *
     */
    public void outputErrorMessageTrackFormat() {
        consoleView.printErrorMessageTrackFormat();
    }

    /**
     * Executes the print of the error message of the {@link MoveListEmptyException}
     */
    public void outputErrorMessageMoveList() {consoleView.printErrorMessageMoveList();}
}