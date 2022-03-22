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
import java.util.Arrays;

/**
 *
 */
public class ConsoleInterface implements UserInterface{

    private final TextIO textIO;
    private final Config config;
    private final TextTerminal<?> textTerminal;

    /**
     * Creates an object of class @{@link ConsoleInterface}
     * which implements all the methods of the @{@link Input} and @{@link Output}
     */
    public ConsoleInterface() {
        textIO = TextIoFactory.getTextIO();
        config = new Config();
        textTerminal = textIO.getTextTerminal();
    }

    @Override
    public File askTrackFile(String[] trackList) throws TracklistEmptyException {
        if (trackList != null) {
            int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(trackList.length - 1).read();
            return new File(config.getTrackDirectory(), trackList[selection]);
        } else {
            throw new TracklistEmptyException();
        }
    }

    @Override
    public int askStrategy(ConfigSpecification.StrategyType[] strategyTypes) {
        return textIO.newIntInputReader().withMinVal(0).withMaxVal(strategyTypes.length - 1).read();
    }

    @Override
    public void printWelcomeText() {
        textTerminal.println("Hallo und willkommen bei Racetrack");
        textTerminal.println("Dieses Spiel macht allen Spass!");
    }

    @Override
    public void printTrackList(File trackDirectory) throws TracklistEmptyException {
        textTerminal.println("Waehle einen Track aus!");
        String[] trackList = trackDirectory.list();
        if (trackList != null) {
            formatListPrinting(trackList);
        } else {
            throw new TracklistEmptyException();
        }
    }

    @Override
    public void printStrategyTypes(ConfigSpecification.StrategyType[] strategyTypes) {
        textTerminal.println("Welche Strategie willst du spielen?");

        //convert StrategyType enum values to Array
        String[] strategies = Arrays.stream(strategyTypes).map(Enum::name).toArray(String[]::new);
        formatListPrinting(strategies);
    }

    @Override
    public PositionVector.Direction askUserMoveDirection() {
        return textIO.newEnumInputReader(PositionVector.Direction.class).read();
    }

    @Override
    public File askSelectedMoveFile(String[] moveDirectory) throws MoveListEmptyException {
        if (moveDirectory != null) {
            int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(moveDirectory.length - 1).read();
            return new File(config.getTrackDirectory(), moveDirectory[selection]);
        } else {
            throw new MoveListEmptyException();
        }
    }

    @Override
    public Config.DialogFeature askForDialogFeature() {
        return textIO.newEnumInputReader(Config.DialogFeature.class).read();
    }

    @Override
    public void printUserDialogFeatures() {
        for (Config.DialogFeature dialogFeature : Config.DialogFeature.values()) {
            textTerminal.println(String.valueOf(dialogFeature));
        }
    }

    /**
     * Prints the appeal for the player to choose a @{@link File} for
     * the @{@link ch.zhaw.pm2.racetrack.strategy.MoveListStrategy}
     * @param moveDirectory directory of the @{@link File}
     * @throws MoveListEmptyException
     */
    public void printMoveList(File moveDirectory) throws MoveListEmptyException {
        textTerminal.println("Waehle eine Datei für deine Moves aus!");
        String[] moveList = moveDirectory.list();
        if (moveList != null) {
            formatListPrinting(moveList);
        } else {
            throw new MoveListEmptyException();
        }
    }

    /**
     * Prints the appeal for the player to choose a @{@link ch.zhaw.pm2.racetrack.PositionVector.Direction}
     * @param moveDirections    Array of all @{@link ch.zhaw.pm2.racetrack.PositionVector.Direction
     */
    public void printNextMoveList(PositionVector.Direction[] moveDirections) {
        //TODO text verbessern
        textTerminal.println("Welche Richtung willst du fahren?");

        String[] directions = Arrays.stream(moveDirections).map(Enum::name).toArray(String[]::new);
        formatListPrinting(directions);
    }

    private void formatListPrinting(String[] list) {
        for (int i = 0; i < list.length; ++i) {
            textTerminal.println(i + " : " + list[i]);
        }
    }
}
