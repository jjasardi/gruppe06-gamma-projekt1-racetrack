package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;

import java.io.File;

/**
 *
 */
public interface UserInterface {

    /**
     *
     */
    void printWelcomeText();

    /**
     * @param trackDirectory
     * @throws TracklistEmptyException
     */
    void printTrackList(File trackDirectory) throws TracklistEmptyException;

    /**
     * @param strategyTypes
     */
    void printStrategyTypes(ConfigSpecification.StrategyType[] strategyTypes);

    /**
     *
     */
    void printMoveList(File moveDirectory) throws MoveListEmptyException ;

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
     *
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
