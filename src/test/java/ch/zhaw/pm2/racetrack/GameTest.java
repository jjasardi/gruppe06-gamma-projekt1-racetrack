package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;
import ch.zhaw.pm2.racetrack.exceptions.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.logic.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameTest {
    private Game game;
    private File trackFile;
    private Track track;

    @BeforeAll
    public void setUp() {
        trackFile = new File("src/test/java/ch/zhaw/pm2/racetrack/test-tracks", "testing-track.txt");
    }

    @BeforeEach
    public void restartGame() throws FileNotFoundException, InvalidTrackFormatException {
        track = new Track(trackFile);
        game = new Game(track);
    }

    @Test
    void switchCarNoCarCrashed() {
        game.switchToNextActiveCar();
        assertEquals(1, game.getCurrentCarIndex());
    }

    @Test
    void switchCarTwoCarsCrashed() {
        game.getCars().get(1).crash();
        game.getCars().get(2).crash();
        game.switchToNextActiveCar();
        assertEquals(3, game.getCurrentCarIndex());
    }

    @Test
    void carMovesToFreePosition() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.RIGHT);
        assertEquals(new PositionVector(20, 1), firstCar.getPosition());
    }

    @Test
    void carCrashesToWall() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.UP);

        assertTrue(firstCar.isCrashed());
        assertEquals(new PositionVector(19, 0), firstCar.getPosition());
    }

    @Test
    void carCrashesIntoRunningCar() {
        Car firstCar = game.getCars().get(0);
        Car secondCar = game.getCars().get(1);
        Car thirdCar = game.getCars().get(2);

        game.doCarTurn(Direction.NONE);
        game.switchToNextActiveCar();
        game.doCarTurn(Direction.NONE);
        game.switchToNextActiveCar();
        game.doCarTurn(Direction.UP);

        assertFalse(firstCar.isCrashed());
        assertFalse(secondCar.isCrashed());
        assertTrue(thirdCar.isCrashed());
        assertEquals(secondCar.getPosition(), thirdCar.getPosition());
    }

    @Test
    void carCrashesIntoCarOnThePath() {
        Car firstCar = game.getCars().get(0);
        Car secondCar = game.getCars().get(1);
        Car thirdCar = game.getCars().get(2);
        game.doCarTurn(Direction.DOWN);
        game.doCarTurn(Direction.DOWN);

        assertTrue(firstCar.isCrashed());
        assertFalse(secondCar.isCrashed());
        assertFalse(thirdCar.isCrashed());
        assertEquals(secondCar.getPosition(), firstCar.getPosition());
    }

    @Test
    void carCrashesIntoCrashedCar() {
        Car thirdCar = game.getCars().get(2);
        thirdCar.crash();
        Car secondCar = game.getCars().get(1);
        game.doCarTurn(Direction.NONE);
        game.switchToNextActiveCar();
        game.doCarTurn(Direction.DOWN);

        assertTrue(secondCar.isCrashed());
        assertEquals(thirdCar.getPosition(), secondCar.getPosition());
    }

    @Test
    void carNotMovingAfterCarCrashesIntoIt() {
        Car secondCar = game.getCars().get(1);
        skipToCar(2);
        game.doCarTurn(Direction.UP);
        skipToCar(1);
        game.doCarTurn(Direction.NONE);
        assertFalse(secondCar.isCrashed());
    }

    @Test
    void allCarsCrashedExceptOne() {
        game.doCarTurn(Direction.NONE);
        game.switchToNextActiveCar();
        game.doCarTurn(Direction.DOWN);
        game.switchToNextActiveCar();
        game.doCarTurn(Direction.DOWN);
        game.switchToNextActiveCar();
        game.doCarTurn(Direction.UP);
        game.switchToNextActiveCar();

        assertEquals(game.getCurrentCarIndex(), game.getWinner());
    }

    @Test
    void carPassesFinishLineInCorrectWay() {
        Car fourthCar = game.getCars().get(3);
        skipToCar(3);
        fourthCar.setPosition(new PositionVector(16, 5));
        game.doCarTurn(Direction.RIGHT);
        assertEquals(new PositionVector(17, 5), fourthCar.getPosition());
        assertEquals(3, game.getWinner());
    }

    @Test
    void carPassesFinishLineInWrongWay() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.LEFT);
        game.doCarTurn(Direction.LEFT);

        assertTrue(firstCar.isCrashed());
        assertEquals(Game.NO_WINNER, game.getWinner());
        assertEquals(new PositionVector(17, 1), firstCar.getPosition());
    }

    @Test
    void carMovesOutsideBoundary() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.RIGHT);
        game.doCarTurn(Direction.RIGHT);
        assertTrue(firstCar.isCrashed());
        assertEquals(new PositionVector(21, 1), firstCar.getPosition());
    }

    private void skipToCar(int carIndex) {
        while (game.getCurrentCarIndex() != carIndex) {
            game.switchToNextActiveCar();
        }
    }

}
