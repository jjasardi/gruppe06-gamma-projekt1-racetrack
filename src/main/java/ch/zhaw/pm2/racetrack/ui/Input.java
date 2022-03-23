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
     * @param output
     * @param config
     */
    public Input(Output output, Config config) {
        this.output = output;
        this.config = config;
    }

    /**
     * @param trackDirectory
     * @return
     * @throws TracklistEmptyException
     */
    public File getSelectedTrackFile(File trackDirectory) throws TracklistEmptyException {
        return consoleInterface.askTrackFile(trackDirectory);
    }

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

    public Direction getUserMoveDirection() {
        return consoleInterface.askUserMoveDirection();
    }

    /**
     * @param moveDirectory
     * @return
     */
    public File getSelectedMoveFile(File moveDirectory) throws MoveListEmptyException {
        return consoleInterface.askSelectedMoveFile(moveDirectory);
    }

    public Config.DialogFeature getDialogFeature(){
        return consoleInterface.askForDialogFeature();
    }
}