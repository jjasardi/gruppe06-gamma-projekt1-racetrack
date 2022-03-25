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
     * @param trackDirectory
     * @throws TracklistEmptyException
     */
    void printTrackList(File trackDirectory) throws TracklistEmptyException;

    /**
     * Prints all the available @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     * @param strategyTypes
     */
    void printStrategyTypes(ConfigSpecification.StrategyType[] strategyTypes);

    /**
     *
     */
    void printMoveList(File moveDirectory) throws MoveListEmptyException ;

    //TODO finish javadoc
    /**
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
     *
     */
    void printErrorMessageTrackFormat();

    /**
     * Prints the error message if the Move List file is empty
     */
    void printErrorMessageMoveList();

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
