package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.io.File;
import java.util.Arrays;

/**
 * This class is for all the output of the game. It uses the TextIO library to
 * print the output in the console.
 *
 * @author Ardi
 */

public class Output {
    private TextIO textIO = TextIoFactory.getTextIO();
    private TextTerminal<?> textTerminal = textIO.getTextTerminal();

    /**
     *
     */
    public void welcomeToRacetrack() {
        textTerminal.println("Hello and welcome to RaceTrack");
        textTerminal.println("This game is fun for everybody!");
    }

    /**
     * @param trackDirectory
     */
    // TODO Tracklist als parameter gut?
    public void outputTrackList(File trackDirectory) throws NullPointerException {
        textTerminal.println("Waehle einen Track aus!");
        String[] trackList = trackDirectory.list();
        if (trackList != null) {
            formatListPrinting(trackList);
        } else {
            throw new NullPointerException();
        }
    }

    /**
     *
     * @param strategyTypes
     */
    // TODO code duplizierung?
    public void outputStrategyTypes(StrategyType[] strategyTypes) {
        textTerminal.println("Welche Strategie willst du spielen?");

        //convert StrategyType enum values to Array
        String[] strategies = Arrays.stream(strategyTypes).map(Enum::name).toArray(String[]::new);
        formatListPrinting(strategies);
    }

    private void formatListPrinting(String[] list) {
        for (int i = 0; i < list.length; ++i) {
            textTerminal.println(i + " : " + list[i]);
        }
    }

    /**
     *
     */
    public void askPlayerAmount() {
        textTerminal.println("Mit wie vielen Personen moechtest du spielen?");
    }
}
