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
     *
     */
    public void outputWelcomeText() {
        consoleView.printWelcomeText();
    }

    /**
     * @param trackDirectory
     * @throws TracklistEmptyException
     */
    public void outputTrackList(File trackDirectory) throws TracklistEmptyException {
        consoleView.printTrackList(trackDirectory);
    }

    /**
     *
     * @param strategyTypes
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
     * @param moveDirections
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
