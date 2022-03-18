package ch.zhaw.pm2.racetrack.logic;

import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.ui.Input;
import ch.zhaw.pm2.racetrack.ui.Output;

import java.io.File;

/**
 *
 */
public class RacetrackFlow {

    private final Config config;
    private final Output output;
    private final Input input;
    private Game game;

    public RacetrackFlow() {
        config = new Config();
        input = new Input();
        output = new Output();
    }


    /**
     * @throws TracklistEmptyException
     */
    public void startGame() throws TracklistEmptyException {
        output.welcomeToRacetrack();
        output.outputTrackList(config.getTrackDirectory());
        File selectedTrackFile = input.getSelectedTrackFile(config.getTrackDirectory());
        //game = new Game();

        output.outputStrategyTypes(ConfigSpecification.StrategyType.values());
    }
}
