package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.given.CarSpecification;
import ch.zhaw.pm2.racetrack.strategy.MoveStrategy;

/**
 * Class representing a car on the racetrack.
 * Uses {@link PositionVector} to store current position on the track grid and current velocity vector.
 * Each car has an identifier character which represents the car on the race track board.
 * Also keeps the state, if the car is crashed (not active anymore). The state can not be changed back to uncrashed.
 * The velocity is changed by providing an acelleration vector.
 * The car is able to calculate the endpoint of its next position and on request moves to it.
 */
public class Car implements CarSpecification {

    /**
     * Car identifier used to represent the car on the track
     */
    private final char id;

    /**
     * Current position of the car on the track grid using a {@link PositionVector}
     */
    private PositionVector position;

    /**
     * Current velocity of the car using a {@link PositionVector}
     */
    private PositionVector velocity = new PositionVector(0, 0);

    /**
     * Indicator if the car has crashed
     */
    private boolean crashed = false;

    /**
     * Current move strategy
     */
    private MoveStrategy moveStrategy;

    /**
     * Constructor for class Car
     * @param id unique Car identification
     * @param position initial position of the Car
     */
    public Car(char id, PositionVector position) {
        this.id = id;
        setPosition(position);
    }

    /**
     * Set this Car position directly, regardless of current position and velocity.
     * This should only be used by the game controller in rare cases to set the crash or winning position.
     * The next position is normaly automatically calculated and set in the {@link #move()} method.
     *
     * @param position The new position to set the car directly to.
     */
    @Override
    public void setPosition(final PositionVector position) {
        this.position = position;
    }

    /**
     * Return the position that will apply after the next move at the current velocity.
     * Does not complete the move, so the current position remains unchanged.
     *
     * @return Expected position after the next move
     */
    @Override
    public PositionVector nextPosition() {
        return PositionVector.add(this.position, this.velocity);
    }

    /**
     * Add the specified amounts to this cars's velocity.
     * The only acceleration values allowed are -1, 0 or 1 in both axis
     * There are 9 possible acceleration vectors, which are defined in {@link PositionVector.Direction}.
     * Changes only velocity, not position.
     *
     * @param acceleration A Direction vector containing the amounts to add to the velocity in x and y dimension
     */
    @Override
    public void accelerate(PositionVector.Direction acceleration) {
        this.velocity = PositionVector.add(this.velocity, acceleration.vector);
    }

    /**
     * Update this Car's position based on its current velocity.
     */
    @Override
    public void move() {
        this.position = PositionVector.add(this.position, this.velocity);
    }

    /**
     * Mark this Car as being crashed.
     */
    @Override
    public void crash() {
        this.crashed = true;
    }

    /**
     * Returns whether this Car has been marked as crashed.
     *
     * @return Returns true if crash() has been called on this Car, false otherwise.
     */
    @Override
    public boolean isCrashed() {
        return crashed;
    }

    /**
     * Set move strategy
     * @param moveStrategy
     */
    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    /**
     * Get current move strategy
     * @return MoveStrategy
     */
    public MoveStrategy getMoveStrategy() {
        return this.moveStrategy;
    }

    public boolean getActiveStatus() {
        return false;
    }

    public char getId() {
        return id;
    }

    public PositionVector getPosition() {
        return position;
    }

    public PositionVector getVelocity() {
        return velocity;
    }

    // Methods used for testing

    /**
     * Get current Position
     * @return PositionVector
     */
    PositionVector getPosition() {
        return this.position;
    }

    PositionVector getVelocity() {
        return this.velocity;
    }
}
