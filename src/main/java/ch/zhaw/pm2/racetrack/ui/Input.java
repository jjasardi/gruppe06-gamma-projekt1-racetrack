package ch.zhaw.pm2.racetrack.ui;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import ch.zhaw.pm2.racetrack.Logic.Config;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;

import java.io.File;

/**
 * This class is for all the input of the game. It uses the TextIO library
 * to read the input
 * from the console.
 * 
 * @author Ardi
 */
public class Input {
    private TextIO textIO = TextIoFactory.getTextIO();
    private Output output = new Output();
    private Config config = new Config();

    public File getSelectedTrackFile() {
        output.outputTrackList(); // TODO hier aufrufen oder in ConsoleApp?
        String[] trackList = config.getTrackDirectory().list();
        assert trackList != null;
        int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(trackList.length - 1).read();
        return new File(config.getTrackDirectory(), trackList[selection]);
    }

    public StrategyType getSelectedStrategyType() {
        output.outputStrategyTypes();
        StrategyType[] strategyTypes = StrategyType.values();
        int selection = textIO.newIntInputReader().withMinVal(0).withMaxVal(strategyTypes.length - 1).read();
        return strategyTypes[selection];
    }

    public void playerCount() {
        textIO.newIntInputReader().withMinVal(1).withMinVal(4).read();
    }
}