package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.logic.RacetrackFlow;

/**
 * This is the Main class of the game Racetrack
 */
public class ConsoleApp {
    public static void main(String[] args) throws TracklistEmptyException {
         new RacetrackFlow();
    }
}
