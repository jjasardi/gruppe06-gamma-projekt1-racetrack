package ch.zhaw.pm2.racetrack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.pm2.racetrack.logic.Config;
import ch.zhaw.pm2.racetrack.logic.Game;

public class GameTest {
    private Game game;
    Config config;
    File trackFile;
    Track track;

    @BeforeAll
    public void setUp() throws FileNotFoundException, InvalidTrackFormatException {
        config = new Config();
        trackFile = new File(config.getTrackDirectory(), "testing-track.txt");
        track = new Track(trackFile);
    }

    @BeforeEach
    public void restartGame() {
        game = new Game(track, track.getCarCount());
    }

    @Test
    public void switchPlayerNoAutoCrashed() {
        game.switchToNextActiveCar();
        assertEquals(1, game.getCurrentCarIndex());
    }

    @Test
    public void switchPlayerTwoAutosCrashed() {
        game.getCars().get(1).isCrashed();
        game.getCars().get(2).isCrashed();
        game.switchToNextActiveCar();
        assertEquals(3, game.getCurrentCarIndex());
    }

    @Test
    public void switchPlayerAllAutosCrashed() {
        game.getCars().get(1).isCrashed();
        game.getCars().get(2).isCrashed();
        game.switchToNextActiveCar();
        assertEquals(3, game.getCurrentCarIndex());
    }
}
