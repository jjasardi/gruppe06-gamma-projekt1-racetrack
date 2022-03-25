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

    /**
     * Test if {@link Game} switches to the right {@link Car} when no cars are crashed
     */
    @Test
    void switchCarNoCarCrashed() {
        game.switchToNextActiveCar();
        assertEquals(1, game.getCurrentCarIndex());
    }

    /**
     * Test if {@link Game} switches to right {@link Car} when two cars are crashed
     */
    @Test
    void switchCarTwoCarsCrashed() {
        game.getCars().get(1).crash();
        game.getCars().get(2).crash();
        game.switchToNextActiveCar();
        assertEquals(3, game.getCurrentCarIndex());
    }

    /**
     * Test if position of car is right after it moves to a position that is not occupied
     */
    @Test
    void carMovesToFreePosition() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.RIGHT);
        assertEquals(new PositionVector(20, 1), firstCar.getPosition());
    }

    /**
     * When {@link Car} moves into a SpaceType, test if
     * <ul>
     *   <li> {@link Car} is marked as crash</li>
     *   <li> {@link Car} has moved to collision position</li>
     * </ul>
     */
    @Test
    void carCrashesToWall() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.UP);

        assertTrue(firstCar.isCrashed());
        assertEquals(new PositionVector(19, 0), firstCar.getPosition());
    }

    /**
     * When one {@link Car} moves into another not-crashed {@link Car}, test if
     * <ul>
     *   <li>the {@link Car}, in which the other {@link Car} moved is not crashed</li>
     *   <li> {@link Car} that moved is crashed</li>
     *   <li> {@link Car} has moved to collision position</li>
     * </ul>
     */
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

    /**
     * When one {@link Car} moves into another {@link Car}, that's on his startPosition-endPosition path test if
     * <ul>
     *   <li>the {@link Car}, that moved is crashed</li>
     *   <li>the {@link Car}, that was on the path is not crashed</li>
     *   <li>crashed {@link Car} has moved to collision position</li>
     * </ul>
     */
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

    /**
     * When one {@link Car} moves into another crashed {@link Car}, test if
     * <ul>
     *   <li> the {@link Car}, that moved is crashed</li>
     *   <li> {@link Car} has moved to collision position</li>
     * </ul>
     */
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

    /**
     * When one {@link Car} doesn't change his position after another {@link Car} has crashed into it, test if
     * <ul>
     *   <li>the {@link Car}, that doesn't move is not crashed</li>
     * </ul>
     */
    @Test
    void carNotMovingAfterCarCrashesIntoIt() {
        Car secondCar = game.getCars().get(1);
        skipToCar(2);
        game.doCarTurn(Direction.UP);
        skipToCar(1);
        game.doCarTurn(Direction.NONE);
        assertFalse(secondCar.isCrashed());
    }

    /**
     * When all {@link Car} crash except one, test if
     * <ul>
     *   <li>the winner of the {@link Game} is the remainig {@link Car}</li>
     * </ul>
     */
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

    /**
     * When one {@link Car} passes the finish line in the correct way, test if
     * <ul>
     *   <li>the {@link Car} moves to the win position</li>
     *   <li>the {@link Car} is the winner of the {@link Game}</li>
     * </ul>
     */
    @Test
    void carPassesFinishLineInCorrectWay() {
        Car fourthCar = game.getCars().get(3);
        skipToCar(3);
        fourthCar.setPosition(new PositionVector(16, 5));
        game.doCarTurn(Direction.RIGHT);
        assertEquals(new PositionVector(17, 5), fourthCar.getPosition());
        assertEquals(3, game.getWinner());
    }

    /**
     * When one {@link Car} passes the finish line in the wrong way, test if
     * <ul>
     *   <li>the {@link Car} is marked as crashed</li>
     *   <li>the {@link Car} is not the winner of the game</li>
     *   <li>the {@link Car} moves to the collision position</li>
     * </ul>
     */
    @Test
    void carPassesFinishLineInWrongWay() {
        Car firstCar = game.getCars().get(0);
        game.doCarTurn(Direction.LEFT);
        game.doCarTurn(Direction.LEFT);

        assertTrue(firstCar.isCrashed());
        assertEquals(Game.NO_WINNER, game.getWinner());
        assertEquals(new PositionVector(17, 1), firstCar.getPosition());
    }

    /**
     * When one {@link Car} moves to a coordinate that's outside coordinate boundaries, test if
     * <ul>
     *   <li>the {@link Car} is marked as crashed</li>
     *   <li>the {@link Car} moves to the collision position</li>
     * </ul>
     */
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
