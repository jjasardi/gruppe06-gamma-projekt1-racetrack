package ch.zhaw.pm2.racetrack.logic;

import ch.zhaw.pm2.racetrack.Car;
import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.Track;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.given.GameSpecification;

import java.util.List;

import static ch.zhaw.pm2.racetrack.PositionVector.Direction;

/**
 * Game controller class, performing all actions to modify the game state.
 * It contains the logic to move the cars, detect if they are crashed
 * and if we have a winner.
 */
public class Game implements GameSpecification {
    public static final int NO_WINNER = -1;
    private static final int FIRST_CAR_INDEX = 0;
    private final Track track;
    private boolean gameHasWinner;
    private int indexCurrentCar;
    private int indexWinner = NO_WINNER;

    /**
     * Creates a Game object
     * @param track  object from the {@link Track} class
     */
    public Game(Track track) {
        this.track = track;
        indexCurrentCar = FIRST_CAR_INDEX;
    }

    /**
     * Return the index of the current active car.
     * Car indexes are zero-based, so the first car is 0, and the last car is getCarCount() - 1.
     * @return The zero-based number of the current car
     */
    @Override
    public int getCurrentCarIndex() {
        return indexCurrentCar;
    }

    /**
     * Get the id of the specified car.
     * @param carIndex The zero-based carIndex number
     * @return A char containing the id of the car
     */
    @Override
    public char getCarId(int carIndex) {
        return track.getCarId(carIndex);
    }

    /**
     * Get the position of the specified car.
     * @param carIndex The zero-based carIndex number
     * @return A PositionVector containing the car's current position
     */
    @Override
    public PositionVector getCarPosition(int carIndex) {
        return track.getCarPos(carIndex);
    }

    /**
     * Get the velocity of the specified car.
     * @param carIndex The zero-based carIndex number
     * @return A PositionVector containing the car's current velocity
     */
    @Override
    public PositionVector getCarVelocity(int carIndex) {
        return track.getCarVelocity(carIndex);
    }

    public Track getTrack() {
        return track;
    }

    public List<Car> getCars() {
        return track.getCars();
    }

    /**
     * Return the winner of the game. If the game is still in progress, returns NO_WINNER.
     * @return The winning car's index (zero-based, see getCurrentCar()), or NO_WINNER if the game is still in progress
     */
    @Override
    public int getWinner() {
        checkIfOnlyOneCarNotCrashed();
        if (gameHasWinner){
            return indexWinner;
        }
        return NO_WINNER;
    }

    /**
     * Execute the next turn for the current active car.
     * <p>This method changes the current car's velocity and checks on the path to the next position,
     * if it crashes (car state to crashed) or passes the finish line in the right direction (set winner state).</p>
     * <p>The steps are as follows</p>
     * <ol>
     *   <li>Accelerate the current car</li>
     *   <li>Calculate the path from current (start) to next (end) position
     *       (see {@link Game#calculatePath(PositionVector, PositionVector)})</li>
     *   <li>Verify for each step what space type it hits:
     *      <ul>
     *          <li>TRACK: check for collision with other car (crashed &amp; don't continue), otherwise do nothing</li>
     *          <li>WALL: car did collide with the wall - crashed &amp; don't continue</li>
     *          <li>FINISH_*: car hits the finish line - wins only if it crosses the line in the correct direction</li>
     *      </ul>
     *   </li>
     *   <li>If the car crashed or wins, set its position to the crash/win coordinates</li>
     *   <li>If the car crashed, also detect if there is only one car remaining, remaining car is the winner</li>
     *   <li>Otherwise move the car to the end position</li>
     * </ol>
     * <p>The calling method must check the winner state and decide how to go on. If the winner is different
     * than {@link Game#NO_WINNER}, or the current car is already marked as crashed the method returns immediately.</p>
     *
     * @param acceleration A Direction containing the current cars acceleration vector (-1,0,1) in x and y direction
     *                     for this turn
     */
    @Override
    public void doCarTurn(Direction acceleration) {
        Car activeCar = track.getCar(indexCurrentCar);
        if (getWinner() != NO_WINNER || activeCar.isCrashed()) {
            return;
        }
        activeCar.accelerate(acceleration);

        List<PositionVector> calculatedPaths = calculatePath(activeCar.getPosition(), activeCar.nextPosition());
        PositionVector stopPosition = activeCar.nextPosition();
        if (verifySpaceTypeForCalculatedPaths(activeCar, calculatedPaths) != null) {
            stopPosition = verifySpaceTypeForCalculatedPaths(activeCar, calculatedPaths);
        }

        if (activeCar.isCrashed() || getWinner() == indexCurrentCar) {
            activeCar.setPosition(stopPosition);
        } else {
            activeCar.move();
        }
    }

    private PositionVector verifySpaceTypeForCalculatedPaths(Car activeCar, List<PositionVector> calculatedPaths) {
        PositionVector stopPosition = null;
        for (int i = 1; i < calculatedPaths.size(); i++) {
            PositionVector calculatedPath = calculatedPaths.get(i);
            ConfigSpecification.SpaceType spaceTypeOnNextPosition = track.getSpaceType(calculatedPath);
            switch (spaceTypeOnNextPosition) {
                case WALL:
                    activeCar.crash();
                    stopPosition = calculatedPath;
                    return stopPosition;

                case TRACK:
                    if (willCarCrash(indexCurrentCar, calculatedPath)) {
                        activeCar.crash();
                        stopPosition = calculatedPath;
                        return stopPosition;
                    }
                    break;

                case FINISH_LEFT, FINISH_RIGHT, FINISH_DOWN, FINISH_UP:
                    PositionVector currentPositionCar = activeCar.getPosition();
                    PositionVector positionSpaceType =  activeCar.nextPosition();
                    ConfigSpecification.SpaceType spaceType = spaceTypeOnNextPosition;
                    if (passedFinishLineInCorrectWay(currentPositionCar,spaceType, positionSpaceType)) {
                        setWinner();
                    } else {
                        activeCar.crash();
                    }
                    stopPosition = calculatedPath;
                    return stopPosition;
            }
        }
        return stopPosition;
    }

    private boolean passedFinishLineInCorrectWay(PositionVector currentPositionCar, ConfigSpecification.SpaceType spaceType, PositionVector positionSpaceType) {
        int currentPositionCarX = currentPositionCar.getX();
        int currentPositionCarY = currentPositionCar.getY();
        int positionSpaceTypeX = positionSpaceType.getX();
        int positionSpaceTypeY = positionSpaceType.getY();

        switch (spaceType.value) {
            case '<':
                if (currentPositionCarX > positionSpaceTypeX) {
                    return true;
                }

            case '>':
                if (currentPositionCarX < positionSpaceTypeX) {
                    return true;
                }
            case '^':
                if (currentPositionCarY > positionSpaceTypeY) {
                    return true;
                }
            case 'v':
                if (currentPositionCarY < positionSpaceTypeY) {
                    return true;
                }
        }
        return false;
    }


    /**
     * Switches to the next car who is still in the game. Skips crashed cars.
     */
    @Override
    public void switchToNextActiveCar() {
        int maxIndex = track.getCarCount();
        int carIndexTemp = (indexCurrentCar + 1) % maxIndex; // result always --> (indexCurrentCar + 1) except when last car --> 0 (return first one)

        while(track.getCar(carIndexTemp).isCrashed()) {
            carIndexTemp++;
            if (carIndexTemp == track.getCarCount()) carIndexTemp = 0;
        }

        indexCurrentCar = carIndexTemp;
    }

    /**
     * Returns all of the grid positions in the path between two positions, for use in determining line of sight.
     * Determine the 'pixels/positions' on a raster/grid using Bresenham's line algorithm.
     * (https://de.wikipedia.org/wiki/Bresenham-Algorithmus)
     * Basic steps are
     * - Detect which axis of the distance vector is longer (faster movement)
     * - for each pixel on the 'faster' axis calculate the position on the 'slower' axis.
     * Direction of the movement has to correctly considered
     * @param startPosition Starting position as a PositionVector
     * @param endPosition Ending position as a PositionVector
     * @return Intervening grid positions as a List of PositionVector's, including the starting and ending positions.
     */
    @Override
    public List<PositionVector> calculatePath(PositionVector startPosition, PositionVector endPosition) {
        return BresenhamAlgorithm.calculatePath(startPosition, endPosition);
    }

    /**
     * Does indicate if a car would have a crash with a WALL space or another car at the given position.
     * @param carIndex The zero-based carIndex number
     * @param position A PositionVector of the possible crash position
     * @return A boolean indicator if the car would crash with a WALL or another car.
     */
    @Override
    public boolean willCarCrash(int carIndex, PositionVector position) {
        Car activeCar = track.getCar(carIndex);
        int xPosition = position.getX();
        int yPosition = position.getY();
        ConfigSpecification.SpaceType spaceType = track.getSpaceType(new PositionVector(xPosition, yPosition));

        if (spaceType == ConfigSpecification.SpaceType.WALL){
            return true;
        } else {
            return willCarCrashWithAnotherCar(activeCar, position);
        }
    }

    private boolean willCarCrashWithAnotherCar(Car activeCar, PositionVector position) {
        boolean willCrash;
        for (Car car : track.getCars()) {
            willCrash = (activeCar.getId() != car.getId() && car.getPosition().equals(position));
            if (willCrash) return willCrash;
        }
        return false;
    }

    private void checkIfOnlyOneCarNotCrashed() {
        int crashCountTemp = 0;
        for (Car car : getCars()) {
            if (car.isCrashed()) {
                crashCountTemp++;
            }
        }
        if ((getCars().size() - crashCountTemp) == 1) {
            setWinner();
        }
    }

    private void setWinner() {
        gameHasWinner = true;
        indexWinner = getCurrentCarIndex();
    }
}