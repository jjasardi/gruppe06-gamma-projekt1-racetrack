package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.pm2.racetrack.PositionVector.Direction;

public class CarTest {
    private Car car;


    @BeforeEach
    public void setUp() {
        car = new Car('1', new PositionVector());
    }

    @Test
    public void negativeAcceleration() {
        car.accelerate(Direction.UP_LEFT);
        assertEquals(new PositionVector(-1, -1), car.getVelocity());
        assertEquals(new PositionVector(), car.getPosition());
    }

    @Test
    public void noAcceleration() {
        car.accelerate(Direction.NONE);
        assertEquals(new PositionVector(0, 0), car.getVelocity());
        assertEquals(new PositionVector(), car.getPosition());
    }

    @Test
    public void positiveAcceleration() {
        car.accelerate(Direction.DOWN_RIGHT);
        assertEquals(new PositionVector(1, 1), car.getVelocity());
        assertEquals(new PositionVector(), car.getPosition());
    }

    @Test
    public void moveWithNoVelocity() {
        car.move();
        assertEquals(new PositionVector(), car.getPosition());
    }

    @Test
    public void moveWithVelocity() {
        car.accelerate(Direction.RIGHT);
        car.move();
    }
}
