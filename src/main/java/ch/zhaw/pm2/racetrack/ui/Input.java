package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;
import ch.zhaw.pm2.racetrack.logic.Config;
import ch.zhaw.pm2.racetrack.strategy.*;

import java.io.File;

/**
 * This class is for all the input of the game.
 */
public class Input {
    private final ConsoleInterface consoleInterface = new ConsoleInterface();
    Config config;
    Output output;

    /**
     * Creats an object of the class @{@link Input}
     * @param output    current @{@link Output} object
     * @param config    current @{@link Config} object
     */
    public Input(Output output, Config config) {
        this.output = output;
        this.config = config;
    }

    /**
     * Provides the by the player selected @{@link File} of @{@link ch.zhaw.pm2.racetrack.Track}
     * @param trackDirectory    directory of the @{@link ch.zhaw.pm2.racetrack.Track}
     * @return  the accutal file of @{@link ch.zhaw.pm2.racetrack.Track}
     * @throws TracklistEmptyException
     */
    public File getSelectedTrackFile(File trackDirectory) throws TracklistEmptyException {
        return consoleInterface.askTrackFile(trackDirectory);
    }

    /**
     * Provides the by the player selected @{@link MoveStrategy}
     * @return  accutal @{@link MoveStrategy}
     * @throws MoveListEmptyException
     */
    public MoveStrategy getSelectedMoveStrategy() throws MoveListEmptyException {
        StrategyType[] strategyTypes = StrategyType.values();
        int selection = consoleInterface.askStrategy(strategyTypes);

        return mapStrategyTypeToMoveStrategy(strategyTypes[selection]);
    }

    private MoveStrategy mapStrategyTypeToMoveStrategy(StrategyType strategyType) throws MoveListEmptyException {
        return switch (strategyType) {
            case DO_NOT_MOVE -> new DoNotMoveStrategy();
            case USER -> new UserMoveStrategy(this, output);
            case MOVE_LIST -> new MoveListStrategy(this, output, config);
            case PATH_FOLLOWER -> new PathFollowerMoveStrategy();
        };
    }

    /**
     * Provides the by the player selected @{@link Direction}
     * @return  choosed @{@link Direction}
     */
    public Direction getUserMoveDirection() {
        return consoleInterface.askUserMoveDirection();
    }

    //TODO finish javadoc
    /**
     *
     * @param moveDirectory
     * @return
     */
    public File getSelectedMoveFile(File moveDirectory) throws MoveListEmptyException {
        return consoleInterface.askSelectedMoveFile(moveDirectory);
    }

    /**
     * Provides by the player selected @{@link Config.DialogFeature}
     * @return choosed @{@link Config.DialogFeature}
     */
    public Config.DialogFeature getDialogFeature(){
        return consoleInterface.askForDialogFeature();
    }
}