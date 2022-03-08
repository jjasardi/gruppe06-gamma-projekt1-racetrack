package ch.zhaw.pm2.racetrack.logic;

import ch.zhaw.pm2.racetrack.Car;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.ui.Input;
import ch.zhaw.pm2.racetrack.ui.Output;

import java.io.File;
import java.util.List;

/**
 *
 */
public class RacetrackManager {

    private final Config config;
    private final Output output;
    private final Input input;
    private Game game;
    
    public RacetrackManager() {
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
        output.askCarsAmount();
        int AMOUNT_OF_CARS = input.askCarsAmount();
        game = new Game(AMOUNT_OF_CARS);

        List<Car> cars = game.getCars();
        for (int i = 1; i <= cars.size(); i++) {
            output.outputStrategyTypes(ConfigSpecification.StrategyType.values());
            cars.get(i).setMoveStrategy(input.getSelectedMoveStrategy());
        }
    }
}
