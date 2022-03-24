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
     * Prints the appeal for the player to choose a @{@link Track}
     * @param trackDirectory    directory of the @{@link Track}
     * @throws TracklistEmptyException
     */
    public void outputTrackList(File trackDirectory) throws TracklistEmptyException {
        consoleView.printTrackList(trackDirectory);
    }

    /**
     * Executes the print of all available @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     * @param strategyTypes array of all @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     */
    public void outputStrategyTypes(StrategyType[] strategyTypes) {
        consoleView.printStrategyTypes(strategyTypes);
    }

    /**
     * @param moveDirectory
     * @throws MoveListEmptyException
     */
    public void outputMoveList(File moveDirectory) throws MoveListEmptyException {
        consoleView.printMoveList(moveDirectory);
    }

    public void outputGameState(String track){
        consoleView.printGameState(track);
    }

    public void outputCurrentCarID(char carID){
        consoleView.printCurrentCarID(carID);
    }

    /**
     * Executes the print of all possibles @{@link ch.zhaw.pm2.racetrack.PositionVector.Direction}
     * @param moveDirections    array of all @{@link ch.zhaw.pm2.racetrack.PositionVector.Direction}
     */
    public void outputNextCommand() {
        consoleView.printNextCommand();
    }

    public void outputDirectionAppeal(){
        consoleView.printDirectionAppeal();
    }

    public void outputUserDialogFeatures(){
        consoleView.printUserDialogFeatures();
    }

    public void outputWinnerText(char carID){
        consoleView.printWinnerText(carID);
    }
}
