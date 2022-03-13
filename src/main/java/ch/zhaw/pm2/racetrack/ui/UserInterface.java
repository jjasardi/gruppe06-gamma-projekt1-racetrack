package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;

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
    public File askTrackFile(String[] trackList) throws TracklistEmptyException;

    /**
     * @param strategyTypes
     * @return
     */
    public int askStrategy(ConfigSpecification.StrategyType[] strategyTypes);

    /**
     *
     */
    public void printWelcomeText();

    /**
     * @param trackDirectory
     * @throws TracklistEmptyException
     */
    public void printTrackList(File trackDirectory) throws TracklistEmptyException;

    /**
     * @param strategyTypes
     */
    public void printStrategyTypes(ConfigSpecification.StrategyType[] strategyTypes);

}
