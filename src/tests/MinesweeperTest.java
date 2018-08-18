/**
 * PSL Challenge - Minesweeper Game
 * Universidad Icesi
 *
 * @author Brayan Henao
 * @version 1.0
 */

package tests;

import exceptions.WrongInputException;
import javafx.util.Pair;
import model.Box;
import model.Minesweeper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class MinesweeperTest {

    // ATTRIBUTES

    /**
     * The relationship with the tested class.
     */
    private Minesweeper game;

    /**
     * The game board to be tested.
     */
    private Box[][] gameBoard;

    /**
     * An array with  all the x,y coordinates of the mines in the scenario.
     */
    private List<Pair<Integer, Integer>> allMines;

    /**
     * The number of rows that the tested minesweeper would have.
     */
    private int rows;

    /**
     * The number of columns that the tested minesweeper would have.
     */
    private int columns;

    /**
     * The number of mines that the tested minesweeper would have.
     */
    private int mines;

    // SCENARIOS

    /**
     * The test scenario, it starts a new game with the provided rows, columns and mines. Also store the mines
     */
    private void setupScenario() {
        this.rows = 10;
        this.columns = 10;
        this.mines = 10;
        game = new Minesweeper(rows, columns, mines);
        game.startGame();
        gameBoard = game.gameBoard.clone();
        allMines = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j].isMine()) {
                    allMines.add(new Pair<>(i, j));
                }
            }
        }
    }

    /**
     * This scenario flag all the mines in the game board
     */

    private void flagMinesScenario() {
        for (Pair mineCoordinates : allMines) {
            int xCoordinate = (int) mineCoordinates.getKey();
            int yCoordinate = (int) mineCoordinates.getValue();
            try {
                game.markBox(xCoordinate, yCoordinate);
            } catch (WrongInputException e) {
                e.printStackTrace();
            }
        }
    }

    // METHODS

    /**
     * This method mark (flag) some random bos that isn't mine or flagged.
     */
    private void markRandomBox() {
        boolean finish = false;
        for (int i = 0; i < rows && !finish; i++) {
            for (int j = 0; j < columns && !finish; j++) {
                if (!gameBoard[i][j].isMine() && !gameBoard[i][j].isFlag()) {
                    gameBoard[i][j].flagBox();
                    finish = true;
                }
            }
        }
    }

    // TESTS

    /**
     * Test that the game is lost.
     */
    @Test
    public void loseGame() {
        setupScenario();
        for (Pair mineCoordinates : allMines) {
            int xCoordinate = (int) mineCoordinates.getKey();
            int yCoordinate = (int) mineCoordinates.getValue();
            game.setLost(false);
            try {
                game.openBox(xCoordinate, yCoordinate);
            } catch (WrongInputException e) {
                e.printStackTrace();
            }
            assertTrue("Error with some mine, game isn't lost yet.", game.getLost());
        }
    }

    /**
     * Test that the game is won.
     */
    @Test
    public void wonGame() {
        setupScenario();
        flagMinesScenario();
        assertTrue("Error with some flag, game isn't won yet.", game.checkIfWin());
    }

    /**
     * Test that the game isn't won yet because there is more flags than mines in the game board (assuming that all the mines
     * are flagged and there is one random box, that is not mine, flagged).
     */
    @Test
    public void markMoreMines() {
        setupScenario();
        flagMinesScenario();
        markRandomBox();
        assertFalse("Error with some flag, game is over, win.", game.checkIfWin());
    }

    /**
     * Test that the game isn't lost yet when all the non mine boxes are uncovered.
     */
    @Test
    public void uncoverAllNotMineBoxes() {
        setupScenario();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!gameBoard[i][j].isMine()) {
                    try {
                        game.openBox(i, j);
                    } catch (WrongInputException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        assertFalse("Error with some uncover box, lost.", game.getLost());
    }

}