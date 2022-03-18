package ch.zhaw.pm2.racetrack.logic;

import ch.zhaw.pm2.racetrack.PositionVector;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BresenhamAlgorithmus {
    /**
     * Returns all of the grid positions in the path between two positions, for use in determining line of sight.
     * Determine the 'pixels/positions' on a raster/grid using Bresenham's line algorithm.
     * (https://de.wikipedia.org/wiki/Bresenham-Algorithmus)
     * Basic steps are
     * - Detect which axis of the distance vector is longer (faster movement)
     * - for each pixel on the 'faster' axis calculate the position on the 'slower' axis.
     * Direction of the movement has to correctly considered
     *
     * @param startPosition Starting position as a {@link PositionVector}.
     * @param endPosition   Ending position as a {@link PositionVector}.
     * @return Intervening grid positions as a List of PositionVector's, including the starting and ending positions.
     */
    public static List<PositionVector> calculatePath(PositionVector startPosition, PositionVector endPosition) {
        List<PositionVector> path = new ArrayList<>();

        // Relative Distance (x & y axis) between end- and starting position
        PositionVector difference = PositionVector.subtract(endPosition, startPosition);

        // Absolute distance (x & y axis) between end- and starting position
        PositionVector distance = new PositionVector(Math.abs(difference.getX()), Math.abs(difference.getY()));

        // Direction of vector on x & y axis (-1: to left/down, 0: none, +1 : to right/up)
        PositionVector direction = new PositionVector(Integer.signum(difference.getX()), Integer.signum(difference.getY()));

        // Determine which axis is the fast direction and set parallel/diagonal step values
        PositionVector parallelStep = new PositionVector();
        PositionVector diagonalStep;
        int distanceSlowAxis, distanceFastAxis;
        if (distance.getX() > distance.getY()) {
            // x axis is the 'fast' direction
            parallelStep.setX(direction.getX());
            distanceSlowAxis = distance.getY();
            distanceFastAxis = distance.getX();
        } else {
            // y axis is the 'fast' direction
            parallelStep.setY(direction.getY());
            distanceSlowAxis = distance.getX();
            distanceFastAxis = distance.getY();
        }
        diagonalStep = new PositionVector(direction);

        PositionVector currentStep = new PositionVector(startPosition);
        path.add(new PositionVector(currentStep));
        int error = distanceFastAxis / 2;

        for (int step = 0; step < distanceFastAxis; step++) {
            error -= distanceSlowAxis;
            if (error < 0) {
                error += distanceFastAxis;
                currentStep.addTo(diagonalStep);
            } else {
                currentStep.addTo(parallelStep);
            }
            path.add(new PositionVector(currentStep));
        }
        return path;
    }
}
