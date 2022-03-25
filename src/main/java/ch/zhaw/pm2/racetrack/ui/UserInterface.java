package ch.zhaw.pm2.racetrack.ui;

import ch.zhaw.pm2.racetrack.PositionVector;
import ch.zhaw.pm2.racetrack.exceptions.MoveListEmptyException;
import ch.zhaw.pm2.racetrack.exceptions.TracklistEmptyException;
import ch.zhaw.pm2.racetrack.given.ConfigSpecification;

import java.io.File;

/**
 * Interface which contains all the User Interface methods
 */
public interface UserInterface {

    /**
     * This method prints the welcome text.
     */
    void printWelcomeText();

    /**
     * Prints all the available @{@link ch.zhaw.pm2.racetrack.Track}
     *
     * @param trackDirectory            the directory where all the tracks files are
     * @throws TracklistEmptyException  exception when track list is empty
     */
    void printTrackList(File trackDirectory) throws TracklistEmptyException;

    /**
     * Prints all the available @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     *
     * @param strategyTypes list of strategy types
     */
    void printStrategyTypes(ConfigSpecification.StrategyType[] strategyTypes);

    /**
     * Prints all the available @{@link ch.zhaw.pm2.racetrack.strategy.MoveStrategy}
     *
     * @param moveDirectory           the directory where all the move files are
     * @throws MoveListEmptyException exception when move list ist empty
     */
    void printMoveList(File moveDirectory) throws MoveListEmptyException ;

    /**
     * Prints the current game state
     *
     * @param track current {@link ch.zhaw.pm2.racetrack.Track} object
     */
    void printGameState(String track);

    /**
     * This method prints the ID of the current car.
     *
     * @param carID char representing the carID
     */
    void printCurrentCarID(char carID);

    /**
     * This method prints the next command.
     */
    void printNextCommand();

    /**
     * This method prints the direction appeal text.
     */
    void printDirectionAppeal();

    /**
     * Prints all the @{@link ch.zhaw.pm2.racetrack.logic.Config.DialogFeature}
     */
    void printUserDialogFeatures();

    /**
     * Prints the text to enter the command again.
     */
    void printNextCommandRepeat();

    /**
     * This method prints the winner text.
     *
     * @param carID char representing the carID
     */
    void printWinnerText(char carID);

    /**
     * This method prints the error message track format.
     */
    void printErrorMessageTrackFormat();

    /**
     * Prints the error message if the Move List file is empty
     */
    void printErrorMessageMoveList();

    /**
     * This method asks the user for the track file.
     *
     * @param trackDirectory            directory where the track files are
     * @return                          new generated track file
     * @throws TracklistEmptyException  exception when the track list is empty
     */
    File askTrackFile(File trackDirectory) throws TracklistEmptyException;

    /**
     * This method asks the user for the move strategy.
     *
     * @param strategyTypes list with the strategy types
     * @return              selected strategy type
     */
    int askMoveStrategy(ConfigSpecification.StrategyType[] strategyTypes);

    /**
     * This method asks for the move file.
     *
     * @param moveDirectory             directory where the move files are
     * @return                          new generated move file
     * @throws MoveListEmptyException   exception when the move file is empty
     */
    File askMoveFile(File moveDirectory) throws MoveListEmptyException;

    /**
     * This methods asks the user for the next option.
     *
     * @return  selected option
     */
    char askOption();

    /**
     * This method asks for the direction
     *
     * @return the selected direction
     */
    PositionVector.Direction askDirection();
}
