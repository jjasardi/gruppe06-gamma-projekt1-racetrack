package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;

import java.io.File;

/**
 * Interface which contains all the User Interface methods
 */
public interface UserInterface {

    /**
     *
     */
    void printWelcomeText();

    /**
     * Prints all the available @{@link ch.zhaw.pm2.racetrack.Track}
     * @param trackDirectory The directory where all the tracks files are
     * @throws TracklistEmptyException
     */
    void printTrackList(File trackDirectory) throws TracklistEmptyException;

    /**
     * Prints all the available @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     * @param strategyTypes
     */
    void printStrategyTypes(ConfigSpecification.StrategyType[] strategyTypes);

    /**
     * Prints all the available @{@link ch.zhaw.pm2.racetrack.MoveListStrategy}
     * @param moveDirectory The directory where all the move files are
     * @throws MoveListEmptyException
     */
    void printMoveList(File moveDirectory) throws MoveListEmptyException ;

    //TODO finish javadoc
    /**
     * prints the current game state
     * @param track
     */
    void printGameState(String track);

    /**
     *
     * @param carID
     */
    void printCurrentCarID(char carID);

    /**
     *
     */
    void printNextCommand();

    /**
     *
     */
    void printDirectionAppeal();

    /**
     * Prints all the @{@link ch.zhaw.pm2.racetrack.logic.Config.DialogFeature}
     */
    void printUserDialogFeatures();

    /**
     * @param carID
     */
    void printWinnerText(char carID);

    /**
     * @param trackList
     * @return
     * @throws TracklistEmptyException
     */
    File askTrackFile(File trackDirectory) throws TracklistEmptyException;

    /**
     * @param strategyTypes
     * @return
     */
    int askMoveStrategy(ConfigSpecification.StrategyType[] strategyTypes);

    /**
     * @param moveDirectory
     * @return
     */
    File askMoveFile(File moveDirectory) throws MoveListEmptyException;

    /**
     *
     * @return
     */
    char askOption();

    /**
     *
     * @return
     */
    PositionVector.Direction askDirection();
}
