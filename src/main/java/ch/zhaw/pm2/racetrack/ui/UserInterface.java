package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.logic.Config;

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
     * @return
     */
    PositionVector.Direction askUserMoveDirection();

    /**
     * @param moveDirectory
     * @return
     */
    File askSelectedMoveFile(File moveDirectory) throws MoveListEmptyException;

    /**
     *
     * @return
     */
    Config.DialogFeature askForDialogFeature();


    /**
     *
     * @return
     */
    PositionVector.Direction askDirection();

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
     * @param carID
     */
    void printWinnerText(char carID);

    /**
     * @param track
     */
    void printGameState(String track);

    /**
     *
     */
    void printUserDialogFeatures();

    char askChoosedOption();

    /**
     *
     */
    void printDirectionAppeal();

    /**
     *
     * @param moveDirections
     */
    void printDirections(PositionVector.Direction[] moveDirections);

    /**
     *
     * @param carID
     */
    void printCurrentCarID(char carID);
}
