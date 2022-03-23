package ch.zhaw.pm2.racetrack.logic;

import ch.zhaw.pm2.racetrack.Car;
import ch.zhaw.pm2.racetrack.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.Track;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.strategy.MoveStrategy;
import ch.zhaw.pm2.racetrack.ui.Input;
import ch.zhaw.pm2.racetrack.ui.Output;

import java.io.File;
import java.io.FileNotFoundException;

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
     * @throws InvalidTrackFormatException
     * @throws MoveListEmptyException
     * @throws TracklistEmptyException
     * @throws FileNotFoundException
     */
    public RacetrackFlow() throws MoveListEmptyException, FileNotFoundException, InvalidTrackFormatException, TracklistEmptyException {
        config = new Config();
        output = new Output();
        input = new Input(output, config);
        startGame();
    }

    private void startGame() throws MoveListEmptyException, FileNotFoundException, InvalidTrackFormatException, TracklistEmptyException {
        setup();
        run();
    }

    private void setup() throws FileNotFoundException, InvalidTrackFormatException, TracklistEmptyException, MoveListEmptyException {
        initializeGame();
        setStrategies();
    }

    private void initializeGame() throws TracklistEmptyException, FileNotFoundException, InvalidTrackFormatException {
        output.welcomeToRacetrack();
        output.outputTrackList(config.getTrackDirectory());
        File selectedTrackFile = input.getSelectedTrackFile(config.getTrackDirectory());
        Track track = new Track(selectedTrackFile);
        game = new Game(track);
    }

    private void setStrategies() throws MoveListEmptyException {
        for (Car car : game.getTrack().getCars()) {
            output.outputStrategyTypes(ConfigSpecification.StrategyType.values());
            car.setMoveStrategy(input.getSelectedMoveStrategy());
        }
    }

    private void run() {
        Track gameTrack = game.getTrack();
        while (game.getWinner() == Game.NO_WINNER) {
            output.outputTrack(gameTrack);
            Car currentCar = gameTrack.getCar(game.getCurrentCarIndex());
            MoveStrategy carStrategy = currentCar.getMoveStrategy();
            game.doCarTurn(carStrategy.nextMove());
            game.switchToNextActiveCar();
        }
//        output.outputWinner(game.getCarId(game.getWinner()));
    }
}
