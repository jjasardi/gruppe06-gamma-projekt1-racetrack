package ch.zhaw.pm2.racetrack.Logic;

import ch.zhaw.pm2.racetrack.Exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.ui.Input;
import ch.zhaw.pm2.racetrack.ui.Output;

/**
 *
 */
public class RacetrackManager {

    Output output;
    Input input;
    
    public RacetrackManager() {
        input = new Input();
        output = new Output();
    }



    public void startGame(){
        output.welcomeToRacetrack();
        output.outputTrackList();
        input.getSelectedTrackFile();
        output.playerCount();
        input.playerCount();
        for (Player player : players) {
            player.chooseStrategy();
        }
    }
}
