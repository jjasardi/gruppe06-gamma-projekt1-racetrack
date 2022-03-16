package ch.zhaw.pm2.racetrack.ui;

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
     *
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

    private void formatListPrinting(String[] list) {
        for (int i = 0; i < list.length; ++i) {
            textTerminal.println(i + " : " + list[i]);
        }
    }
}
