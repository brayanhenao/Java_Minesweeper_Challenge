package interfaces;

import exceptions.WrongInputException;

public interface IMinesweeperGame {

    // ATTRIBUTES

    /**
     * Method that initializes all the attributes needed to start the game.
     */
    void startGame();

    /**
     * Method that opens a box.
     */
    void openBox(int row, int column) throws WrongInputException;

    /**
     * Method that marks a box (flag).
     */
    void markBox(int row, int column) throws WrongInputException;

    /**
     * Method that checks if the player win.
     */
    boolean checkIfWin();

    /**
     * Method that prints the board solved.
     */
    void solveGame();

    /**
     * Method that prints the actual state of the game board.
     */
    void displayGameBoard();

    /**
     * Method that prints the actual state of the game board and reveal mines.
     */
    void revealMines();
}
