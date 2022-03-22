package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.logic.Config;
import ch.zhaw.pm2.racetrack.logic.Game;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameTest {
    private Game game;
    private Config config;
    private File trackFile;
    private Track track;

    @BeforeAll
    public void setUp() throws FileNotFoundException, InvalidTrackFormatException {
        config = new Config();
        trackFile = new File(config.getTrackDirectory(), "testing-track.txt");
        track = new Track(trackFile);
    }

    @BeforeEach
    public void restartGame() {
        game = new Game(track);
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
        game.doCarTurn(Direction.UP);
        assertTrue(firstCar.isCrashed());
        assertEquals(new PositionVector(19, 0), firstCar.getPosition());
    }

    @Test
    public void carCrashesIntoRunningCar() {
        Car secondCar = game.getCars().get(1);
        Car thirdCar = game.getCars().get(2);
        game.doCarTurn(Direction.NONE);
        game.doCarTurn(Direction.NONE);
        game.doCarTurn(Direction.UP);
        assertTrue(thirdCar.isCrashed());
        assertFalse(secondCar.isCrashed());
        assertEquals(secondCar.getPosition(), thirdCar.getPosition());
    }

    @Test
    public void carCrashesIntoCarOnThePath() {
        Car firstCar = game.getCars().get(0);
        Car secondCar = game.getCars().get(1);
        Car thirdCar = game.getCars().get(2);
        game.doCarTurn(Direction.DOWN);
        skipThreeCars();
        game.doCarTurn(Direction.DOWN);
        assertTrue(firstCar.isCrashed());
        assertFalse(secondCar.isCrashed());
        assertFalse(thirdCar.isCrashed());
        assertEquals(secondCar.getPosition(), firstCar.getPosition());
    }

    @Test
    public void carCrashesIntoCrashedCar() {
        Car thirdCar = game.getCars().get(2);
        thirdCar.crash();
        Car secondCar = game.getCars().get(1);
        game.doCarTurn(Direction.NONE);
        game.doCarTurn(Direction.DOWN);
        assertTrue(secondCar.isCrashed());
        assertEquals(thirdCar.getPosition(), secondCar.getPosition());
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
        skipThreeCars();
        game.doCarTurn(Direction.DOWN_LEFT);
        skipThreeCars();
        game.doCarTurn(Direction.UP_LEFT);
        skipThreeCars();
        game.doCarTurn(Direction.RIGHT);
        assertEquals(3, game.getWinner());
    }

    @Test
    public void carPassesFinishLineInWrongWay() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.LEFT);
        skipThreeCars();
        game.doCarTurn(Direction.LEFT);
        assertTrue(firstCar.isCrashed());
        assertEquals(Game.NO_WINNER, game.getWinner());
        assertEquals(new PositionVector(17, 1), firstCar.getPosition());
    }

    private void skipThreeCars() {
        for (int i = 0; i < 3; ++i) {
            game.switchToNextActiveCar();
        }
    }

}
