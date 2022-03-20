package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.logic.Config;
import ch.zhaw.pm2.racetrack.logic.Game;

public class GameTest {
    private Game game;
    Config config;
    File trackFile;
    Track track;

    @BeforeAll
    public void setUp() throws FileNotFoundException, InvalidTrackFormatException {
        config = new Config();
        trackFile = new File(config.getTrackDirectory(), "testing-track.txt");
        track = new Track(trackFile);
    }

    @BeforeEach
    public void restartGame() {
        game = new Game(track, track.getCarCount());
    }

    @Test
    public void switchPlayerNoCarCrashed() {
        game.switchToNextActiveCar();
        assertEquals(1, game.getCurrentCarIndex());
    }

    @Test
    public void switchPlayerTwoCarsCrashed() {
        game.getCars().get(1).crash();
        game.getCars().get(2).crash();
        game.switchToNextActiveCar();
        assertEquals(3, game.getCurrentCarIndex());
    }

    @Test
    public void carMovesToFreePosition() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.RIGHT);
        assertEquals(new PositionVector(20, 1), firstCar.getPosition());
    }

    @Test
    public void carCrashesToWall() {
        Car firstCar = game.getCars().get(0);
        PositionVector positionBeforeCrash = firstCar.getPosition();
        game.doCarTurn(Direction.UP);
        assertTrue(firstCar.isCrashed());
        assertEquals(positionBeforeCrash, firstCar.getPosition());
    }

    @Test
    public void carCrashesIntoRunningCar() {
        Car secondCar = game.getCars().get(1);
        Car thirdCar = game.getCars().get(2);
        PositionVector positionBeforeCrash = thirdCar.getPosition();
        game.doCarTurn(Direction.NONE);
        game.doCarTurn(Direction.NONE);
        game.doCarTurn(Direction.UP);
        assertTrue(thirdCar.isCrashed());
        assertFalse(secondCar.isCrashed());
        assertEquals(positionBeforeCrash, thirdCar.getPosition());
    }

    @Test
    public void carCrashesIntoCrashedCar() {
        game.getCars().get(2).crash();
        Car secondCar = game.getCars().get(1);
        PositionVector positionBeforeCrash = secondCar.getPosition();
        game.doCarTurn(Direction.NONE);
        game.doCarTurn(Direction.DOWN);
        assertTrue(secondCar.isCrashed());
        assertEquals(positionBeforeCrash, secondCar.getPosition());
    }

    @Test
    public void allCarsCrashedExceptOne() {
        game.doCarTurn(Direction.NONE);
        game.doCarTurn(Direction.DOWN);
        game.doCarTurn(Direction.UP);
        game.doCarTurn(Direction.UP);
        assertEquals(game.getCurrentCarIndex(), game.getWinner());
    }

    @Test
    public void carPassesFinishLineInCorrectWay() {
        skipFirstThreeCars();
        game.doCarTurn(Direction.DOWN_LEFT);
        skipFirstThreeCars();
        game.doCarTurn(Direction.UP_LEFT);
        skipFirstThreeCars();
        game.doCarTurn(Direction.RIGHT);
        assertEquals(3, game.getWinner());
    }

    @Test
    public void carPassesFinishLineInWrongWay() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.LEFT);
        skipFirstThreeCars();
        game.doCarTurn(Direction.LEFT);
        assertTrue(firstCar.isCrashed());
        assertEquals(Game.NO_WINNER, game.getWinner());
    }

    private void skipFirstThreeCars() {
        for (int i = 0; i < 3; ++i) {
            game.switchToNextActiveCar();
        }
    }

}
