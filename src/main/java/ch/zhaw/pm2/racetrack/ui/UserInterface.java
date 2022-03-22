package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.logic.Config;

import java.io.File;
import java.io.ObjectInputFilter;

/**
 *
 */
public interface UserInterface {

    /**
     * @param trackList
     * @return
     * @throws TracklistEmptyException
     */
    File askTrackFile(String[] trackList) throws TracklistEmptyException;

    /**
     * @param strategyTypes
     * @return
     */
    int askStrategy(ConfigSpecification.StrategyType[] strategyTypes);

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
     * @return
     */
    PositionVector.Direction askUserMoveDirection();

    /**
     * @param moveDirectory
     * @return
     */
    File askSelectedMoveFile(String[] moveDirectory) throws MoveListEmptyException;

    Config.DialogFeature askForDialogFeature();

    void printUserDialogFeatures();

    void printWinnerText(char carID);
}
