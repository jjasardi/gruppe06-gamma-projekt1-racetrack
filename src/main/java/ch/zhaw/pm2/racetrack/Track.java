package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.given.TrackSpecification;
import ch.zhaw.pm2.racetrack.logic.Config;
import ch.zhaw.pm2.racetrack.logic.TrackReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    private TrackReader trackReader;
    private List<Car> cars;
    private List<Config.SpaceType> trackSpaceTypes;
    private final Integer width;
    private final Integer height;

    /**
     * Initialize a Track from the given track file.
     *
     * @param trackFile Reference to a file containing the track data
     * @throws FileNotFoundException       if the given track file could not be found
     * @throws InvalidTrackFormatException if the track file contains invalid data (no tracklines, no
     */
    public Track(File trackFile) throws FileNotFoundException, InvalidTrackFormatException {
        cars = new ArrayList<>();
        trackSpaceTypes = new ArrayList<>();
        trackReader = new TrackReader(trackFile);
        width = trackReader.getWidth();
        height = trackReader.getHeight();
        addCarAndSpaceTypeToArrayList();
    }

    public List<Car> getCars() {
        return cars;
    }

    /**
     * Returns the {@link Config.SpaceType} from a given {@link PositionVector}
     *
     * @param positionVector position to retrieve {@link Config.SpaceType} from
     * @return {@link Config.SpaceType}
     */
    @Override
    public Config.SpaceType getSpaceType(PositionVector positionVector) {
        int index = trackReader.getTrackPositionVector().indexOf(positionVector);
        return trackSpaceTypes.get(index);
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

    private PositionVector getPositionVectorFromIndex(int index) {
        return trackReader.getTrackPositionVector().get(index);
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
        for (Car car : cars) {
            if (car.getPosition().getX() == x && car.getPosition().getY() == y) {
                if (car.isCrashed()) {
                    return CRASH_INDICATOR;
                } else {
                    return car.getId();
                }
            }
        }
        return currentSpace.getValue();
    }

    /**
     * Returns a String representation of the given track
     *
     * @return string of the given track
     */
    @Override
    public String toString() {
        StringBuilder track = new StringBuilder();
        int lengthOfRow = width - 1;
        for (int lengthOfTrack = 0; lengthOfTrack < trackReader.getTrackCharacters().size(); lengthOfTrack++) {
            if (lengthOfTrack == lengthOfRow && lengthOfRow != trackReader.getTrackCharacters().size() - 1) {
                track.append(trackReader.getTrackCharacters().get(lengthOfTrack)).append("\n");
                lengthOfRow += width;
            } else {
                track.append(trackReader.getTrackCharacters().get(lengthOfTrack));
            }
        }
        return track.toString();
    }

    private void addCarAndSpaceTypeToArrayList() throws InvalidTrackFormatException {
        for (Character character : trackReader.getTrackCharacters()) {
            if (isCar(character)) {
                initializeAndAddValidCarToList(character, getPositionVectorFromIndex(trackReader.getTrackCharacters().indexOf(character)));
            } else {
                trackSpaceTypes.add(trackReader.getSpaceTypeOfCharacter(character));
            }
        }
    }

    private static Boolean isCar(Character character) {
        return character.toString().matches("([^# <>v^])+");
    }

    private void initializeAndAddValidCarToList(Character character, PositionVector position) throws InvalidTrackFormatException {
        Car car = new Car(character, new PositionVector(position.getX(), position.getY()));
        cars.add(car);
    }
}
