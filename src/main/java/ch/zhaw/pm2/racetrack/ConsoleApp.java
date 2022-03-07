package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.logic.RacetrackManager;

/**
 *
 */
public class ConsoleApp {
    public static void main(String[] args) throws TracklistEmptyException {
         new RacetrackManager().startGame();
    }
}
