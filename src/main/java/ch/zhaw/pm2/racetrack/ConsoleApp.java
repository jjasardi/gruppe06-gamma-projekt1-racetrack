package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.logic.RacetrackFlow;

import java.io.FileNotFoundException;

/**
 *
 */
public class ConsoleApp {
    public static void main(String[] args) throws TracklistEmptyException, FileNotFoundException, InvalidTrackFormatException, MoveListEmptyException {
         new RacetrackFlow();
    }
}
