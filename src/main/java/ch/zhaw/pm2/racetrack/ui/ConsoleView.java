package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;
import ch.zhaw.pm2.racetrack.logic.Config;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 *
 */
public class ConsoleView implements UserInterface {

    private final TextIO textIO;
    private final Config config;
    private final TextTerminal<?> textTerminal;

    /**
     * Creates an object of class @{@link ConsoleInterface}
     * which implements all the methods of the @{@link Input} and @{@link Output}
     */
    public ConsoleView() {
        textIO = TextIoFactory.getTextIO();
        config = new Config();
        textTerminal = textIO.getTextTerminal();
    }

    @Override
    public void printWelcomeText() {
        textTerminal.println("Hello and welcome to Racetrack!\n");
    }

    @Override
    public void printTrackList(File trackDirectory) throws TracklistEmptyException {
        textTerminal.println("Choose a track to play with!");
        String[] trackList = trackDirectory.list(txtFilter);
        if (trackList != null) {
            formatListPrinting(trackList);
        } else {
            throw new TracklistEmptyException("No Track file found in directory!");
        }
    }

    @Override
    public void printStrategyTypes(ConfigSpecification.StrategyType[] strategyTypes) {
        textTerminal.println("Which strategy do you want to play with?");

        //convert StrategyType enum values to Array
        String[] strategies = Arrays.stream(strategyTypes).map(Enum::name).toArray(String[]::new);
        formatListPrinting(strategies);
    }

    /**
     * @param moveDirectory
     */
    @Override
    public void printMoveList(File moveDirectory) throws MoveListEmptyException {
        textTerminal.println("Choose a file for the defined moves!");
        String[] moveList = moveDirectory.list(txtFilter);
        if (moveList != null) {
            formatListPrinting(moveList);
        } else {
            throw new MoveListEmptyException("No Move List file found in directory!");
        }
    }

    private void formatListPrinting(String[] list) {
        for (int i = 0; i < list.length; ++i) {
            textTerminal.println(i + " : " + list[i]);
        }
    }

    @Override
    public void printGameState(String track) {
        textTerminal.print(track);
    }

    @Override
    public void printCurrentCarID(char carID) {
        textTerminal.println("Playing car: " +carID);
    }

    /**
     *
     */
    @Override
    public void printNextCommand() {
        textTerminal.println("Press a key!");
        textTerminal.println("d: enter direction // h: help // t: show track // q: quit game");
    }

    @Override
    public void printDirectionAppeal() {
        textTerminal.println("Enter a direction!");
        textTerminal.println("Acceleration directions 1,2,3,4,5,6,7,8,9: ");
    }

    @Override
    public void printUserDialogFeatures() {
        textTerminal.print("Acceleration Directions: \n" +
                "1  2  3    1 = UP-LEFT     2 = UP      3 = UP-RIGHT\n" +
                "4  5  6    4 = LEFT        5 = NONE    6 = RIGHT\n" +
                "7  8  9    7 = DOWN-LEFT   8 = DOWN    9 = DOWN-RIGHT\n");
        textTerminal.println("h for help");
        textTerminal.println("t to show track");
        textTerminal.println("q to quit game");
    }

    @Override
    public void printWinnerText(char carID) {
        textTerminal.print("The car " + carID + " wins!");
        textTerminal.print("Good job! Congratulations!");
    }

    /**
     *
     */
    @Override
    public void printErrorMessageTrackFormat() {
        textTerminal.print("\nThe choosed track does not fit the required format guidelines!\n\n");
    }

    @Override
    public void printErrorMessageMoveList() {
        textTerminal.print("\nThe choosed Move List file is empty!\n\n");
    }

    @Override
    public File askTrackFile(File trackDirectory) throws TracklistEmptyException {
            String[] trackList = trackDirectory.list(txtFilter);
            if (trackList != null) {
                int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(trackList.length - 1).read();
                return new File(config.getTrackDirectory(), trackList[selection]);
            } else {
                throw new TracklistEmptyException("No Track file found in directory!");
            }
    }

    @Override
    public int askMoveStrategy(ConfigSpecification.StrategyType[] strategyTypes) {
        return textIO.newIntInputReader().withMinVal(0).withMaxVal(strategyTypes.length - 1).read();
    }

    /**
     * @param moveDirectory
     * @return
     * @throws MoveListEmptyException
     */
    @Override
    public File askMoveFile(File moveDirectory) throws MoveListEmptyException {
        String[] moveList = moveDirectory.list(txtFilter);
        if (moveList != null) {
            int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(moveList.length - 1).read();
            return new File(config.getMoveDirectory(), moveList[selection]);
        } else {
            throw new MoveListEmptyException("No Move List file found in directory!");
        }
    }

    /**
     * @return
     */
    @Override
    public char askOption() {
        return textIO.newCharInputReader().read();
    }

    @Override
    public PositionVector.Direction askDirection() {
        return textIO.newEnumInputReader(PositionVector.Direction.class).read();
    }

    private FilenameFilter txtFilter = new FilenameFilter() {

        @Override
        public boolean accept(File f, String name)
        {
            return name.endsWith("txt");
        }
    };

}
