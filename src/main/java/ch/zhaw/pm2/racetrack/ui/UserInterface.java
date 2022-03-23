package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.Track;
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
     * @param trackList
     * @return
     * @throws TracklistEmptyException
     */
    File askTrackFile(File trackDirectory) throws TracklistEmptyException;

    /**
     * @return
     */
    ConfigSpecification.StrategyType askStrategy();

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
    File askSelectedMoveFile(File moveDirectory) throws MoveListEmptyException;

    Config.DialogFeature askForDialogFeature();

    /**
     *
     */
    void printUserDialogFeatures();

    /**
     *
     */
    void printTrack(Track track);

    void printWinnerText(char carID);
}
