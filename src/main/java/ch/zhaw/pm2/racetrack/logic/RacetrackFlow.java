package ch.zhaw.pm2.racetrack.logic;

import ch.zhaw.pm2.racetrack.Car;
import ch.zhaw.pm2.racetrack.Track;
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

    /**
     *
     */
    public RacetrackFlow() {
        config = new Config();
        output = new Output();
        input = new Input(output, config);
        startGame();
    }

    /**
     * @throws TracklistEmptyException
     */
    private void startGame() {
        setup();
    }

    private void setup() {
        initializeGame();
        setStrategies();
        output.welcomeToRacetrack();
        output.outputTrackList(config.getTrackDirectory());
        File selectedTrackFile = input.getSelectedTrackFile(config.getTrackDirectory());
        Track track = new Track(selectedTrackFile);
        game = new Game(track, track.getCarCount());
        for (Car car : game.getTrack().getCars()) {
            output.outputStrategyTypes(ConfigSpecification.StrategyType.values());
            car.setMoveStrategy(input.getSelectedMoveStrategy());
        }
    }

    private void initializeGame() {
        output.welcomeToRacetrack();
        output.outputTrackList(config.getTrackDirectory());
        File selectedTrackFile = input.getSelectedTrackFile(config.getTrackDirectory());
        Track track = new Track(selectedTrackFile);
        game = new Game(track);
    }

    private void setStrategies() {
        for (Car car : game.getTrack().getCars()) {
            output.outputStrategyTypes(ConfigSpecification.StrategyType.values());
            car.setMoveStrategy(input.getSelectedMoveStrategy());
        }
    }
}
