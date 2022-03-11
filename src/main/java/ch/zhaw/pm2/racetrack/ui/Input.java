package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;
import ch.zhaw.pm2.racetrack.logic.Config;
import ch.zhaw.pm2.racetrack.strategy.*;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static ch.zhaw.pm2.racetrack.given.ConfigSpecification.MAX_CARS;

/**
 * This class is for all the input of the game. It uses the TextIO library
 * to read the input
 * from the console.
 * 
 * @author Ardi
 */
public class Input {
    private static TextIO textIO;
    private final Config config;

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
     * @throws TracklistEmptyException
     */
    public File getSelectedTrackFile(File trackDirectory) throws TracklistEmptyException {
        String[] trackList = trackDirectory.list();
        if (trackList != null) {
            int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(trackList.length - 1).read();
            return new File(config.getTrackDirectory(), trackList[selection]);
        } else {
            throw new TracklistEmptyException();
        }
    }

    public MoveStrategy getSelectedMoveStrategy() {
        StrategyType[] strategyTypes = StrategyType.values();
        int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(strategyTypes.length - 1).read();

        DoNotMoveStrategy doNotMoveStrategy = new DoNotMoveStrategy();
        UserMoveStrategy userMoveStrategy = new UserMoveStrategy(this);
        MoveListStrategy moveListStrategy = new MoveListStrategy();
        PathFollowerMoveStrategy pathFollowerMoveStrategy = new PathFollowerMoveStrategy();

        Map<StrategyType, MoveStrategy> strategyMap = new HashMap<>();
        strategyMap.put(StrategyType.DO_NOT_MOVE, doNotMoveStrategy);
        strategyMap.put(StrategyType.USER, userMoveStrategy);
        strategyMap.put(StrategyType.MOVE_LIST, moveListStrategy);
        strategyMap.put(StrategyType.PATH_FOLLOWER, pathFollowerMoveStrategy);

        return strategyMap.get(strategyTypes[selection]);
    }

    /**
     * @return
     */
    public int askPlayerAmount() {
        final int MIN_PLAYER = 1;
        final int MAX_PLAYERS = MAX_CARS;
        return textIO.newIntInputReader().withMinVal(MIN_PLAYER).withMaxVal(MAX_PLAYERS).read();
    }

    /**
     *
     * @return
     */
    public PositionVector.Direction askUserMoveDirection() {
        return textIO.newEnumInputReader(PositionVector.Direction.class).read();
    }
}