package ch.zhaw.pm2.racetrack.logic;

import ch.zhaw.pm2.racetrack.Car;
import ch.zhaw.pm2.racetrack.Track;
import ch.zhaw.pm2.racetrack.exceptions.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.strategy.MoveStrategy;
import ch.zhaw.pm2.racetrack.strategy.UserMoveStrategy;
import ch.zhaw.pm2.racetrack.ui.Input;
import ch.zhaw.pm2.racetrack.ui.Output;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class represents the the different phases of the game and is controlling
 * the game. It starts the game and goes through all the different phases until
 * one player wins the game.
 */
public class RacetrackFlow {

    private final Config config;
    private final Output output;
    private final Input input;
    private Game game;

    /**
     * Creates a RacetrackFlow object
     */
    public RacetrackFlow() {
        config = new Config();
        output = new Output();
        input = new Input(output, config);
        startGame();
    }

    private void startGame() {
        setup();
        run();
    }

    private void nextCommand(char pressedKey, MoveStrategy carStrategy) {
        switch (pressedKey) {
            case ('d') -> game.doCarTurn(carStrategy.nextMove());
            case ('h') -> output.outputUserDialogFeatures();
            case ('t') -> output.outputGameState(game.getTrack().toString());
            case ('q') -> System.exit(0);
            default -> throw new IllegalArgumentException();
        }
    }

    private void setup()  {
        initializeGame();
        setStrategies();
    }

    private void initializeGame()  {
        output.outputWelcomeText();
        output.outputTrackList(config.getTrackDirectory());
        try {
            File selectedTrackFile = input.getSelectedTrackFile(config.getTrackDirectory());
            Track track = new Track(selectedTrackFile);
            game = new Game(track);
        } catch (InvalidTrackFormatException exception) {
            output.outputErrorMessageTrackFormat();
            initializeGame();
        } catch (FileNotFoundException exception) {
            System.err.println("File not found!");
            System.exit(0);
        }
    }

    private void setStrategies() {
        try {
            for (Car car : game.getTrack().getCars()) {
                output.outputStrategyTypes(ConfigSpecification.StrategyType.values());
                car.setMoveStrategy(input.getSelectedMoveStrategy());
            }
        } catch (MoveListEmptyException exception){
            exception.getMessage();
        }
    }

    private void run() {
        Track gameTrack = game.getTrack();
        while (game.getWinner() == Game.NO_WINNER) {
            output.outputGameState(gameTrack.toString());
            Car currentCar = gameTrack.getCar(game.getCurrentCarIndex());
            output.outputCurrentCarID(currentCar.getId());
            MoveStrategy carStrategy = currentCar.getMoveStrategy();
            if(carStrategy instanceof UserMoveStrategy){
                output.outputNextCommand();
                char pressedKey = input.getChoosedOption();
                nextCommand(pressedKey, carStrategy);
            } else {
                game.doCarTurn(carStrategy.nextMove());
            }
            game.switchToNextActiveCar();
        }
        output.outputGameState(gameTrack.toString());
        output.outputWinnerText(game.getCarId(game.getWinner()));
    }
}
