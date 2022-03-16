package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;

import java.io.File;

/**
 * This class is for all the output of the game. It uses the TextIO library to
 * print the output in the console.
 *
 * @author Ardi
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

    // TODO MoveListEmptyException
    public void outputMoveList(File moveDirectory) throws MoveListEmptyException {
        textTerminal.println("Waehle eine Datei für deine Moves!");
        String[] moveList = moveDirectory.list();
        if (moveList != null) {
            formatListPrinting(moveList);
        } else {
            throw new MoveListEmptyException();
        }
    }

    /**
     *
     * @param strategyTypes
     */
    public void outputStrategyTypes(StrategyType[] strategyTypes) {
        consoleInterface.printStrategyTypes(strategyTypes);
    }

}
