package ch.zhaw.pm2.racetrack.logic;

import ch.zhaw.pm2.racetrack.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.PositionVector;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class TrackReader {

    private Integer width;
    private Integer height;
    private ArrayList<String> trackLines;
    private List<Character> trackCharacters;
    private List<PositionVector> trackPositionVectors;

    /**
     * @param trackFile
     */
    public TrackReader(File trackFile) throws FileNotFoundException, InvalidTrackFormatException {
        trackLines = new ArrayList<>();
        trackCharacters = new ArrayList<>();
        trackPositionVectors = new ArrayList<>();

        readLineByLineIntoArray(initializeReader(trackFile));

        addCharacterFromLineToArrayList();

        addPositionVectorToArrayList();
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public List<Character> getTrackCharacters() {
        return trackCharacters;
    }

    public List<PositionVector> getTrackPositionVector() {
        return trackPositionVectors;
    }

    private BufferedReader initializeReader(File trackFile) throws FileNotFoundException {
        BufferedReader reader;
        if (trackFile.exists()) {
            FileInputStream fileStream = new FileInputStream(trackFile);
            InputStreamReader input = new InputStreamReader(fileStream);
            reader = new BufferedReader(input);
        } else {
            throw new FileNotFoundException();
        }
        return reader;
    }

    private void readLineByLineIntoArray(BufferedReader reader) throws InvalidTrackFormatException {
        try {
            String line;
            trackLines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                width = line.length();
                if (width > 1) {
                    trackLines.add(line);
                }
            }
            height = trackLines.size();
            reader.close();
        } catch (IOException e) {
//            throw new InvalidTrackFormatException("Something went wrong - File couldn't be read.");
        }
    }

    private void addCharacterFromLineToArrayList() {
        for (String line : trackLines) {
            for (String character : line.split("")) {
                trackCharacters.add(character.charAt(0));
            }
        }
    }

    /**
     * @param character
     * @return
     */
    public Config.SpaceType getSpaceTypeOfCharacter(Character character) {
        return switch (character) {
            case ('#') -> Config.SpaceType.WALL;
            case (' ') -> Config.SpaceType.TRACK;
            case ('^') -> Config.SpaceType.FINISH_UP;
            case ('v') -> Config.SpaceType.FINISH_DOWN;
            case ('<') -> Config.SpaceType.FINISH_LEFT;
            case ('>') -> Config.SpaceType.FINISH_RIGHT;
            default -> throw new RuntimeException("Invalid character was given as parameter!");
        };
    }

    private void addPositionVectorToArrayList() {
        for (int currentColumn = 0; currentColumn < height; currentColumn++) {
            for (int currentRow = 0; currentRow < width; currentRow++) {
                trackPositionVectors.add(new PositionVector(currentRow, currentColumn));
            }
        }
        trackPositionVectors = Collections.unmodifiableList(trackPositionVectors);
    }
}
