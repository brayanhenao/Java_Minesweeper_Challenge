/**
 * PSL Challenge - Minesweeper Game
 * Universidad Icesi
 *
 * @author Brayan Henao
 * @version 1.0
 */

package model;

import exceptions.WrongInputException;
import interfaces.IGraph;
import interfaces.IMinesweeperGame;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Minesweeper implements IGraph<Box>, IMinesweeperGame {

    // ATTRIBUTES

    /**
     * The rows in the game board.
     */
    private int rows;

    /**
     * The columns in the game board.
     */
    private int columns;

    /**
     * The number of mines in the game board.
     */
    private int mines;

    /**
     * Attribute to check if the player already lost the game.
     */
    private boolean lost;

    /**
     * The array of boxes. The game board.
     */
    public Box[][] gameBoard;

    /**
     * Constructor of the Minesweeper class.
     */
    public Minesweeper(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        gameBoard = new Box[rows][columns];
    }

    // METHODS

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRows() {
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumns() {
        return columns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNode(Box node) {
        int xCoordinate = node.getXCoordinate();
        int yCoordinate = node.getYCoordinate();
        if (xCoordinate > 0) {
            node.addAdjacentBox(gameBoard[xCoordinate - 1][yCoordinate]);
        }
        // LEFT
        if (yCoordinate > 0) {
            node.addAdjacentBox(gameBoard[xCoordinate][yCoordinate - 1]);
        }
        // BOTTOM
        if (xCoordinate < (rows - 1)) {
            node.addAdjacentBox(gameBoard[xCoordinate + 1][yCoordinate]);
        }

        // RIGHT
        if (yCoordinate < (columns - 1)) {
            node.addAdjacentBox(gameBoard[xCoordinate][yCoordinate + 1]);
        }

        // TOP LEFT DIAGONAL
        if (xCoordinate > 0 && yCoordinate > 0) {
            node.addAdjacentBox(gameBoard[xCoordinate - 1][yCoordinate - 1]);
        }

        // TOP RIGHT DIAGONAL
        if (xCoordinate > 0 && yCoordinate < (columns - 1)) {
            node.addAdjacentBox(gameBoard[xCoordinate - 1][yCoordinate + 1]);
        }

        // BOTTOM RIGHT DIAGONAL
        if (xCoordinate < (rows - 1) && yCoordinate < (columns - 1)) {
            node.addAdjacentBox(gameBoard[xCoordinate + 1][yCoordinate + 1]);
        }

        // BOTTOM LEFT DIAGONAL
        if (xCoordinate < (rows - 1) && yCoordinate > 0) {
            node.addAdjacentBox(gameBoard[xCoordinate + 1][yCoordinate - 1]);
        }

        gameBoard[xCoordinate][yCoordinate] = node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void breadthFirstSearch(Box origin) {
        Queue<Box> queue = new LinkedList<>();
        ((LinkedList<Box>) queue).push(origin);

        while (!queue.isEmpty()) {
            Box selected = queue.poll();
            int xCoordinate = selected.getXCoordinate();
            int yCoordinate = selected.getYCoordinate();
            gameBoard[xCoordinate][yCoordinate].visitBox();

            List<Box> adjacent = selected.getAdjacentBoxes();

            for (Box adjacentBox : adjacent) {

                int adjacentXCoordinate = adjacentBox.getXCoordinate();
                int adjacentYCoordinate = adjacentBox.getYCoordinate();

                if (validateBSFCondition(xCoordinate, yCoordinate)) {
                    gameBoard[adjacentXCoordinate][adjacentYCoordinate].visitBox();
                    if (gameBoard[adjacentXCoordinate][adjacentYCoordinate].getMinesAround() == 0) {
                        ((LinkedList<Box>) queue).push(adjacentBox);
                    }
                }
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        initializeBoard();
        fillAdjacentBoxes();
        fillBoardWithMines();
        fillBoardWithFreeBoxes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openBox(int row, int column) throws WrongInputException {
        Box selectedBox = gameBoard[row][column];
        if (selectedBox != null) {
            if (!selectedBox.isVisited()) {
                gameBoard[row][column].visitBox();
                if (selectedBox.isMine()) {
                    setLost(true);
                } else {
                    breadthFirstSearch(selectedBox);
                }
            }
        } else {
            throw new WrongInputException("Invalid row or column");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void markBox(int row, int column) throws WrongInputException {
        Box selectedBox = gameBoard[row][column];
        if (selectedBox != null) {
            if (!selectedBox.isVisited()) {
                gameBoard[row][column].flagBox();
            }
        } else {
            throw new WrongInputException("Invalid row or column");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkIfWin() {
        int correctMinesFlagged = 0;
        int flagsCounter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j].isFlag()) {
                    flagsCounter++;
                    if (gameBoard[i][j].isMine()) {
                        correctMinesFlagged++;
                    }
                }
            }
        }
        if (correctMinesFlagged == mines && flagsCounter == mines) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void solveGame() {
        StringBuilder printedBoard = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            printedBoard.append("|\t");
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j].isMine()) {
                    printedBoard.append(Box.MINE_CHAR);
                } else {
                    printedBoard.append(gameBoard[i][j].getMinesAround());
                }
                printedBoard.append("\t");
            }
            printedBoard.append("|\n");
        }
        System.out.println(printedBoard.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayGameBoard() {
        printBoard(gameBoard, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void revealMines() {
        printBoard(gameBoard, true);
    }

    /**
     * Method that validates the breadth first search traversal condition (if the current box doesn't have any mines
     * around, it should visit all the boxes around).
     *
     * @param xCoordinate - the x coordinate of the current box.
     * @param yCoordinate - the y coordinate of the current box.
     */
    private boolean validateBSFCondition(int xCoordinate, int yCoordinate) {
        return !gameBoard[xCoordinate][yCoordinate].isVisited() && !gameBoard[xCoordinate][yCoordinate].isMine() && !gameBoard[xCoordinate][yCoordinate].isFlag();
    }

    /**
     * Method that print a board passes as parameter.
     *
     * @param board  - the board to be printed.
     * @param solved - if the board to be printed is solved (to reveal mines).
     */
    private void printBoard(Box[][] board, boolean solved) {
        StringBuilder printedBoard = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            printedBoard.append("|\t");
            for (int j = 0; j < columns; j++) {
                if (solved && board[i][j].isMine()) {
                    printedBoard.append(Box.MINE_CHAR);
                } else {
                    printedBoard.append(board[i][j].getValue());
                }
                printedBoard.append("\t");
            }
            printedBoard.append("|\n");
        }
        System.out.println(printedBoard.toString());
    }

    /**
     * Method that checks if the player lost the game.
     *
     * @return boolean - if the player lost the game.
     */
    public boolean getLost() {
        return lost;
    }

    /**
     * Method that sets if the player lost the game.
     *
     * @param lost- if the player lost the game.
     */
    public void setLost(boolean lost) {
        this.lost = lost;
    }

    /**
     * Method that initialize all the board (crating every box).
     */
    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Box box = new Box(i, j, Box.UNSELECTED_CELL_CHAR);
                gameBoard[i][j] = box;
            }
        }
    }

    /**
     * Method that adds the adjacent boxes to every box.
     */
    private void fillAdjacentBoxes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                addNode(gameBoard[i][j]);
            }
        }
    }

    /**
     * Method that initialize all the board (creating every box).
     */
    private void fillBoardWithMines() {
        int minesLeft = mines;
        Random random = new Random();

        while (minesLeft > 0) {
            int xCordinate = random.nextInt(Integer.MAX_VALUE) % rows;
            int yCordinate = random.nextInt(Integer.MAX_VALUE) % columns;

            if (!gameBoard[xCordinate][yCordinate].isMine()) {
                gameBoard[xCordinate][yCordinate].setType(Box.MINE_CHAR);
                gameBoard[xCordinate][yCordinate].setMine(true);
                minesLeft--;
            }
        }
    }

    /**
     * Method that fill all the free boxes in the board with the number of mines around of each one.
     */
    private void fillBoardWithFreeBoxes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!gameBoard[i][j].isMine()) {
                    int minesAround = numberOfMinesAround(i, j);
                    gameBoard[i][j].setMinesAround(minesAround);
                }
            }
        }
    }

    /**
     * Method that calculate the number of mines around of a given box.
     *
     * @param i - the x coordinate of the box to calculate the number of mines around.
     * @param j - the y coordinate of the bos to calculate the number of mines around.
     * @return int - the number of ines around the given box.
     */
    private int numberOfMinesAround(int i, int j) {
        Box selectedBox = gameBoard[i][j];
        List<Box> adjacentBoxes = selectedBox.getAdjacentBoxes();
        int minesAround = 0;
        for (Box adjacentBox : adjacentBoxes) {
            if (adjacentBox.isMine()) {
                minesAround++;
            }
        }
        return minesAround;
    }
}
