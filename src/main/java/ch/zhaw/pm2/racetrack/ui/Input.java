package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;
import ch.zhaw.pm2.racetrack.logic.Config;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.io.File;

/**
 * This class is for all the input of the game. It uses the TextIO library
 * to read the input
 * from the console.
 * 
 * @author Ardi
 */
public class Input {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final Config config = new Config();

    /**
     * @param trackDirectory
     * @return
     * @throws TracklistEmptyException
     */
    public File getSelectedTrackFile(File trackDirectory) throws TracklistEmptyException {
        String[] trackList = trackDirectory.list();
        if (trackList != null) {
            int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(trackList.length - 1).read();
            return new File(config.getTrackDirectory(), trackList[selection]);
        } else {
            throw new TracklistEmptyException();
        }
    }

    public StrategyType getSelectedStrategyType() {
        StrategyType[] strategyTypes = StrategyType.values();
        int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(strategyTypes.length - 1).read();
        return strategyTypes[selection];
    }

    /**
     * @return
     */
    public int askPlayerAmount() {
        return textIO.newIntInputReader().withMinVal(1).withMaxVal(4).read();
    }
}