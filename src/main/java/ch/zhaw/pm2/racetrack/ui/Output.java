package ch.zhaw.pm2.racetrack.ui;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import ch.zhaw.pm2.racetrack.Config;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;

/**
 * This class is for all the output of the game. It uses the TextIO library to
 * print the output in the console.
 * 
 * @author Ardi
 */

public class Output {
    private Config config;
    private TextIO textIO = TextIoFactory.getTextIO();
    private TextTerminal<?> textTerminal = textIO.getTextTerminal();

    // TODO Tracklist als parameter gut?
    public void outputTrackList() {
        String[] trackList = config.getTrackDirectory().list();
        for (int i = 0; i <= trackList.length; ++i) {
            textTerminal.println(i + " : " + trackList[i]);
        }
    }

    // TODO code duplizierung?
    public void outputStrategyTypes(){
        for (int i = 0; i <= StrategyType.values().length; ++i){
            textTerminal.println(i + " : " + StrategyType.values()[i]);
        }
    }
}
