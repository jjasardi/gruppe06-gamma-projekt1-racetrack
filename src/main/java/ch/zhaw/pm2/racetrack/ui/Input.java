package ch.zhaw.pm2.racetrack.ui;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;
import ch.zhaw.pm2.racetrack.logic.Config;
import ch.zhaw.pm2.racetrack.strategy.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is for all the input of the game. It uses the TextIO library
 * to read the input
 * from the console.
 * 
 * @author Ardi
 */
public class Input {
    private final Config config = new Config();
    private final ConsoleInterface consoleInterface = new ConsoleInterface();

    /**
     *
     */
    public Input() {
        textIO = TextIoFactory.getTextIO();
        config = new Config();
    }

    public static TextIO getInputInstance() {
        return textIO;
    }
    /**
     * @param trackDirectory
     * @return
     */
    public File getSelectedTrackFile(File trackDirectory) throws NullPointerException {
        String[] trackList = trackDirectory.list();
        return consoleInterface.askTrackFile(trackList);
    }

    public MoveStrategy getSelectedMoveStrategy() {
        StrategyType[] strategyTypes = StrategyType.values();
        int selection = consoleInterface.askStrategy(strategyTypes);

        return mapStrategyTypeToMoveStrategy(strategyTypes[selection]);
    }

    private static MoveStrategy mapStrategyTypeToMoveStrategy(StrategyType strategyType) {
        DoNotMoveStrategy doNotMoveStrategy = new DoNotMoveStrategy();
        UserMoveStrategy userMoveStrategy = new UserMoveStrategy(this);
        MoveListStrategy moveListStrategy = new MoveListStrategy();
        PathFollowerMoveStrategy pathFollowerMoveStrategy = new PathFollowerMoveStrategy();

        Map<StrategyType, MoveStrategy> strategyMap = new HashMap<>();
        strategyMap.put(StrategyType.DO_NOT_MOVE, doNotMoveStrategy);
        strategyMap.put(StrategyType.USER, userMoveStrategy);
        strategyMap.put(StrategyType.MOVE_LIST, moveListStrategy);
        strategyMap.put(StrategyType.PATH_FOLLOWER, pathFollowerMoveStrategy);

        return strategyMap.get(strategyType);
    }
}