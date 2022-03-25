package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.exceptions.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.SpaceType;
import ch.zhaw.pm2.racetrack.given.TrackSpecification;
import ch.zhaw.pm2.racetrack.logic.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the racetrack board.
 *
 * <p>The racetrack board consists of a rectangular grid of 'width' columns and 'height' rows.
 * The zero point of he grid is at the top left. The x-axis points to the right and the y-axis points downwards.</p>
 * <p>Positions on the track grid are specified using {@link PositionVector} objects. These are vectors containing an
 * x/y coordinate pair, pointing from the zero-point (top-left) to the addressed space in the grid.</p>
 *
 * <p>Each position in the grid represents a space which can hold an enum object of type {@link Config.SpaceType}.<br>
 * Possible Space types are:
 * <ul>
 *  <li>WALL : road boundary or off track space</li>
 *  <li>TRACK: road or open track space</li>
 *  <li>FINISH_LEFT, FINISH_RIGHT, FINISH_UP, FINISH_DOWN :  finish line spaces which have to be crossed
 *      in the indicated direction to winn the race.</li>
 * </ul>
 * <p>Beside the board the track contains the list of cars, with their current state (position, velocity, crashed,...)</p>
 *
 * <p>At initialization the track grid data is read from the given track file. The track data must be a
 * rectangular block of text. Empty lines at the start are ignored. Processing stops at the first empty line
 * following a non-empty line, or at the end of the file.</p>
 * <p>Characters in the line represent SpaceTypes. The mapping of the Characters is as follows:</p>
 * <ul>
 *   <li>WALL : '#'</li>
 *   <li>TRACK: ' '</li>
 *   <li>FINISH_LEFT : '&lt;'</li>
 *   <li>FINISH_RIGHT: '&gt;'</li>
 *   <li>FINISH_UP   : '^;'</li>
 *   <li>FINISH_DOWN: 'v'</li>
 *   <li>Any other character indicates the starting position of a car.<br>
 *       The character acts as the id for the car and must be unique.<br>
 *       There are 1 to {@link Config#MAX_CARS} allowed. </li>
 * </ul>
 *
 * <p>All lines must have the same length, used to initialize the grid width).
 * Beginning empty lines are skipped.
 * The the tracks ends with the first empty line or the file end.<br>
 * An {@link InvalidTrackFormatException} is thrown, if
 * <ul>
 *   <li>not all track lines have the same length</li>
 *   <li>the file contains no track lines (grid height is 0)</li>
 *   <li>the file contains more than {@link Config#MAX_CARS} cars</li>
 * </ul>
 *
 * <p>The Track can return a String representing the current state of the race (including car positons)</p>
 */
public class Track implements TrackSpecification {

    public static final char CRASH_INDICATOR = 'X';

    private List<Car> cars;
    private int sizeX = 0;
    private int sizeY = 0;
    private final SpaceType[][] trackGrid;
    private final List<String> trackStringList = new ArrayList<>();

    /**
     * Initialize a Track from the given track file.
     *
     * @param trackFile Reference to a file containing the track data
     * @throws FileNotFoundException       if the given track file could not be found
     * @throws InvalidTrackFormatException if the track file contains invalid data (no tracklines, no
     */
    public Track(File trackFile) throws FileNotFoundException, InvalidTrackFormatException {
        cars = new ArrayList<>();
        if (!trackFile.exists())
            throw new FileNotFoundException();
        try {
            scanFile(trackFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        if (!isValidTrack()) ;
        trackGrid = new SpaceType[sizeX][sizeY];
        convertStringToTrack();
    }

    public List<Car> getCars() {
        return cars;
    }

    private boolean isValidTrack() throws InvalidTrackFormatException {
        return isRectangular() && hasValidCharacters() && hasFinishLines() && validCarID();
    }

    private void scanFile(File trackFile) throws IOException {
        Scanner scanner = new Scanner(trackFile, StandardCharsets.UTF_8);
        while (scanner.hasNext()) {
            trackStringList.add(scanner.nextLine());
        }
    }

    private boolean isRectangular() throws InvalidTrackFormatException {
        boolean isRectangle = true;
        if (trackStringList.size() == 0) {
            isRectangle = false;
        } else {
            sizeX = trackStringList.get(sizeY).length();
            sizeY++;
            while (sizeY < trackStringList.size() && isRectangle) {
                isRectangle = trackStringList.get(sizeY).length() == sizeX;
                sizeY++;
            }
        }
        if (!isRectangle) throw new InvalidTrackFormatException("Track is not a rectangle.");
        return isRectangle;
    }

    private boolean hasValidCharacters() throws InvalidTrackFormatException {
        int nonTrackChars = 0;
        for (String line : trackStringList) {
            for (char c : line.toCharArray()) {
                if (!(isTrackChar(c) || c == '*')) nonTrackChars++;
            }
        }
        if (!(nonTrackChars > 0)) throw new InvalidTrackFormatException("Track has no cars.");
        if (!(nonTrackChars <= Config.MAX_CARS)) throw new InvalidTrackFormatException("Track has nto many cars.");
        return nonTrackChars > 0 && nonTrackChars <= ConfigSpecification.MAX_CARS;
    }

    private boolean hasFinishLines() throws InvalidTrackFormatException {
        boolean hasfinishLine = false;
        char finishLineDirection = 'N';
        for (String line : trackStringList) {
            for (char c : line.toCharArray()) {
                if (isFinishLine(c)) {
                    if (finishLineDirection == 'N') {
                        finishLineDirection = c;
                        hasfinishLine = true;
                    }
                    hasfinishLine = hasfinishLine && finishLineDirection == c;
                }
            }
        }
        if (finishLineDirection == 'N') throw new InvalidTrackFormatException("Track has no finishline.");
        if (!hasfinishLine) throw new InvalidTrackFormatException("Track has multiple finishlines.");
        return hasfinishLine;
    }

    private boolean validCarID() throws InvalidTrackFormatException {
        List<Character> chars = new ArrayList<>();
        chars.add(CRASH_INDICATOR);
        for (String line : trackStringList) {
            for (char c : line.toCharArray()) {
                if (!(isTrackChar(c) || c == '*')) {
                    for (Character takenCarIDs : chars) {
                        if (c == takenCarIDs) {
                            throw new InvalidTrackFormatException("Car :" + c + " has a invalid ID.");
                        }
                    }
                    chars.add(c);
                }
            }
        }
        return true;
    }

    private void convertStringToTrack() {
        int x = 0, y = 0;
        for (String line : trackStringList) {
            for (char c : line.toCharArray()) {
                if (isTrackChar(c)) {
                    trackGrid[x][y] = charToSpaceType(c);
                } else if (c == '*') {
                    trackGrid[x][y] = SpaceType.TRACK;
                } else {
                    trackGrid[x][y] = SpaceType.TRACK;
                    cars.add(new Car(c, new PositionVector(x, y)));
                }
                x++;
            }
            x = 0;
            y++;
        }
    }


    /**
     * Returns the {@link Config.SpaceType} from a given {@link PositionVector}
     *
     * @param position position to retrieve {@link Config.SpaceType} from
     * @return {@link Config.SpaceType}
     */
    @Override
    public Config.SpaceType getSpaceType(PositionVector position) {
        if (position.getX() >= 0 && position.getX() <= sizeX && position.getY() >= 0 && position.getY() <= sizeY) {
            return trackGrid[position.getX()][position.getY()];
        } else {
            return SpaceType.WALL;
        }
    }


    /**
     * Get instance of specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return The car instance at the given index
     */
    @Override
    public Car getCar(int carIndex) {
        return cars.get(carIndex);
    }

    /**
     * Return the number of cars.
     *
     * @return Number of cars
     */
    @Override
    public int getCarCount() {
        return cars.size();
    }

    /**
     * Get the id of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A char containing the id of the car
     */
    @Override
    public char getCarId(int carIndex) {
        return cars.get(carIndex).getId();
    }

    /**
     * Get the position of the specified car.
     *
     * @param carIndex The zero-based carIndex number
     * @return A PositionVector containing the car's current position
     */
    @Override
    public PositionVector getCarPos(int carIndex) {
        return cars.get(carIndex).getPosition();
    }

    @Override
    public PositionVector getCarVelocity(int carIndex) {
        return cars.get(carIndex).getVelocity();
    }

    /**
     * Gets character at the given position.
     * If there is a crashed car at the position, {@link #CRASH_INDICATOR} is returned.
     *
     * @param y            position Y-value
     * @param x            position X-vlaue
     * @param currentSpace char to return if no car is at position (x,y)
     * @return character representing position (x,y) on the track
     */
    @Override
    public char getCharAtPosition(int y, int x, Config.SpaceType currentSpace) {
        if (x > sizeX || y > sizeY) {
            System.err.println("Parameter Value out of Bound");
            throw new UnsupportedOperationException();
        }
        char charAtPos = currentSpace.value;
        for (Car car : cars) {
            if (car.getPosition().getY() == y && car.getPosition().getX() == x) {
                if (car.isCrashed()) {
                    charAtPos = CRASH_INDICATOR;
                } else {
                    charAtPos = car.getId();
                }
            }
        }
        return charAtPos;
    }

    /**
     * Returns a String representation of the given track
     *
     * @return string of the given track
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int currentY = 0; currentY < sizeY; currentY++) {
            for (int currentX = 0; currentX < sizeX; currentX++) {
                string.append(getCharAtPosition(currentY, currentX, getSpaceType(new PositionVector(currentX, currentY))));
            }
            string.append("\n");
        }
        return string.toString();
    }

    /**
     * @param c
     * @return
     */
    public static SpaceType charToSpaceType(char c) {
        SpaceType returnValue = null;
        for (SpaceType space : SpaceType.values()) {
            if (space.value == c) returnValue = space;
        }
        return returnValue;
    }

    /**
     * @param charAtPos
     * @return
     */
    public static boolean isTrackChar(char charAtPos) {
        return isFinishLine(charAtPos) || charAtPos == SpaceType.TRACK.getValue() || charAtPos == SpaceType.WALL.getValue();
    }

    /**
     * @param charAtPos
     * @return
     */
    public static boolean isFinishLine(char charAtPos) {
        return isFinishLineDown(charAtPos) ||
                isFinishLineUp(charAtPos) ||
                isFinishLineLeft(charAtPos) ||
                isFinishLineRight(charAtPos);
    }

    /**
     * @param spaceType
     * @return
     */
    public static boolean isFinishLineDown(char spaceType) {
        return spaceType == SpaceType.FINISH_DOWN.getValue();
    }

    /**
     * @param spaceType
     * @return
     */
    public static boolean isFinishLineUp(char spaceType) {
        return spaceType == SpaceType.FINISH_UP.getValue();
    }

    /**
     * @param spaceType
     * @return
     */
    public static boolean isFinishLineLeft(char spaceType) {
        return spaceType == SpaceType.FINISH_LEFT.getValue();
    }

    /**
     * @param spaceType
     * @return
     */
    public static boolean isFinishLineRight(char spaceType) {
        return spaceType == SpaceType.FINISH_RIGHT.getValue();
    }
}
