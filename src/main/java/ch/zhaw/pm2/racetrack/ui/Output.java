package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.Exceptions.TracklistEmptyException;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import ch.zhaw.pm2.racetrack.Logic.Config;
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

    public void welcomeToRacetrack() {
        textTerminal.println("Hello and welcome to RaceTrack");
        textTerminal.println("This game is fun for everybody!");
    }
    // TODO Tracklist als parameter gut?
    public void outputTrackList() throws TracklistEmptyException {
        textTerminal.println("Waehle einen Track aus!");
        String[] trackList = config.getTrackDirectory().list();
        if (trackList != null) {
            for (int i = 0; i <= trackList.length; ++i) {
                textTerminal.println(i + " : " + trackList[i]);
            }
        } else {
            throw new TracklistEmptyException();
        }
    }

    // TODO code duplizierung?
    public void outputStrategyTypes(){
        textTerminal.println("Welche Strategie willst du spielen?");
        for ()
        for (int i = 0; i <= StrategyType.values().length; ++i){
            textTerminal.println(i + " : " + StrategyType.values()[i]);
        }
    }

    public void playerCount() {
        textTerminal.println("Mit wie vielen Personen moechtest du spielen?");
    }
}
