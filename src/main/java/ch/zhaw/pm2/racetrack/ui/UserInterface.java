package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.logic.Config;

import java.io.File;
import java.io.ObjectInputFilter;

/**
 * Interface which contains all the User Interface methods
 */
public interface UserInterface {

    /**
     * Asks the player which @{@link ch.zhaw.pm2.racetrack.Track} he wants to play with
     * @param trackList available track files
     * @return the @{@link File} he choosed
     * @throws TracklistEmptyException
     */
    File askTrackFile(File trackDirectory) throws TracklistEmptyException;

    /**
     * Asks the player which @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy} he wants to play with
     * @param strategyTypes
     * @return
     */
    int askStrategy(ConfigSpecification.StrategyType[] strategyTypes);

    /**
     * Prints the welcome text for the game
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
     * Asks player to choose a @{@link ch.zhaw.pm2.racetrack.PositionVector.Direction}
     * @return
     */
    PositionVector.Direction askUserMoveDirection();

    //TODO finish javadoc
    /**
     * Asks player to choose a @{@link File} for the @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     * @param moveDirectory array of all @{@link ch.zhaw.pm2.racetrack.PositionVector.Direction}
     * @return
     */
    File askSelectedMoveFile(File moveDirectory) throws MoveListEmptyException;

    /**
     * Asks the player to choose a optinal @{@link Config.DialogFeature}
     * @return  choosed @{@link ch.zhaw.pm2.racetrack.logic.Config.DialogFeature}
     */
    Config.DialogFeature askForDialogFeature();

    /**
     * Prints all the @{@link ch.zhaw.pm2.racetrack.logic.Config.DialogFeature}
     */
    void printUserDialogFeatures();

    /**
     * @param carID
     */
    void printWinnerText(char carID);

    /**
     * @param track
     */
    void printGameState(String track);
}
