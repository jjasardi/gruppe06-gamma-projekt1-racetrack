package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.exceptions.InvalidTrackFormatException;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.logic.RacetrackFlow;

import java.io.FileNotFoundException;

/**
 * This is the Main class of the game Racetrack
 */
public class ConsoleApp {
    public static void main(String[] args) {
         new RacetrackFlow();
    }
}
