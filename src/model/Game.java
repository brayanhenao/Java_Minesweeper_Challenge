/**
 * PSL Challenge - Minesweeper Game
 * Universidad Icesi
 *
 * @author Brayan Henao
 * @version 1.0
 */

package model;

import exceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {

    // CONSTANTS

    /**
     * Constant used to represent the user default character to mark (flag) a box.
     */
    private final static char MARK_CHAR = 'M';

    /**
     * Constant used to represent the user default character to uncover (visit) a box.
     */
    private final static char UNCOVER_CHAR = 'U';

    // ATTRIBUTES

    /**
     * The reader class used to input the user information.
     */
    private BufferedReader reader;

    /**
     * Relationship to the Minesweeper class that represents the game.
     */
    private Minesweeper minesweeper;

    /**
     * The String attribute used to store the reader information.
     */
    private String line;

    /**
     * Represents if the game is already finished.
     */
    private boolean gameFinish;

    /**
     * Constructor of the Game class.
     */
    public Game() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        gameFinish = false;
    }

    // METHODS

    /**
     * Method that starts the program, creating the game board by reading the user input information.
     */
    private void startPlaying() {
        int rows, columns, mines;
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("------------------- Author: Brayan Andrés Henao ---------------------------------");
        System.out.println("----------------------- Universidad Icesi ---------------------------------------");
        System.out.println("---------------------------------------------------------------------------------");

        System.out.println("INPUT THE HEIGHT, WIDTH AND NUMBER OF MINES (SEPARATED BY A BLANK SPACE EACH ONE)");
        do {
            try {
                line = reader.readLine();
                String[] initialInfo = line.split(" ");
                try {
                    rows = Integer.parseInt(initialInfo[0]);
                    columns = Integer.parseInt(initialInfo[1]);
                    mines = Integer.parseInt(initialInfo[2]);

                    minesweeper = new Minesweeper(rows, columns, mines);
                    minesweeper.startGame();
                    minesweeper.displayGameBoard();
                    userMovements();
                    reader.close();
                } catch (NumberFormatException e) {
                    System.out.println("Error: only numbers allowed");
                }
            } catch (IOException e) {
                System.out.println("Error: wrong input format");
            }
        } while (!gameFinish);
    }

    /**
     * Method that start the console interaction with the user(movements such as mark and uncover a box).
     *
     * @throws IOException
     */
    private void userMovements() throws IOException {
        int row, column;
        char action;
        while (!gameFinish && (line = reader.readLine()) != null) {
            String[] playerMovement = line.split(" ");
            row = Integer.parseInt(playerMovement[0]);
            column = Integer.parseInt(playerMovement[1]);
            action = playerMovement[2].charAt(0);
            row--;
            column--;
            if (row >= 0 && row < minesweeper.getRows() && column >= 0 && column < minesweeper.getColumns()) {
                if (action == MARK_CHAR) {
                    try {
                        minesweeper.markBox(row, column);
                    } catch (WrongInputException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (action == UNCOVER_CHAR) {
                    try {
                        minesweeper.openBox(row, column);
                    } catch (WrongInputException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Wrong movement character");
                }
                if (minesweeper.getLost()) {
                    System.out.println("You lost!");
                    minesweeper.revealMines();
                    gameFinish = true;
                } else if (minesweeper.checkIfWin()) {
                    minesweeper.displayGameBoard();
                    System.out.println("Congratulations, you win!");
                    gameFinish = true;
                } else {
                    minesweeper.displayGameBoard();
                }
            } else {
                System.out.println("Incorrect row or column number.");
            }
        }
    }

    /**
     * Main method of the class.
     *
     * @param args
     */
    public static void main(String[] args) {
        Game mineSweeperGame = new Game();
        mineSweeperGame.startPlaying();
    }
}
