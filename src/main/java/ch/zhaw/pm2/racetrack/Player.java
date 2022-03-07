package ch.zhaw.pm2.racetrack;

import ch.zhaw.pm2.racetrack.given.ConfigSpecification.StrategyType;

/**
 * The class Player represents a player of the game.
 */
public class Player {
    private final String name;
    private final Car car;
    private StrategyType strategyType;

    public Player(int playerNumber, Car car) {
        name = "Player " + playerNumber;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }
}
