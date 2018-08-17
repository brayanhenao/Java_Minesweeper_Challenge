package model;

import exceptions.WrongInputException;
import interfaces.IGraph;
import interfaces.INode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Minesweeper implements IGraph<Box> {


    private int rows;

    private int columns;

    private int mines;

    private boolean lost;

    public Box[][] gameBoard;

    public Minesweeper(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        gameBoard = new Box[rows][columns];
        initializeBoard();
        fillAdjacentBoxes();
        fillBoardWithMines();
        fillBoardWithFreeBoxes();
    }

    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Box box = new Box(i, j, Box.UNSELECTED_CELL_CHAR);
                gameBoard[i][j] = box;
            }
        }
    }

    private void fillAdjacentBoxes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                addNode(gameBoard[i][j]);
            }
        }
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public void addNode(Box node) {
        int xCordinate = node.getXCoordinate();
        int yCordinate = node.getYCoordinate();
        if (xCordinate > 0) {
            node.addAdjacentNode(gameBoard[xCordinate - 1][yCordinate]);
        }
        // LEFT
        if (yCordinate > 0) {
            node.addAdjacentNode(gameBoard[xCordinate][yCordinate - 1]);
        }
        // BOTTOM
        if (xCordinate < (rows - 1)) {
            node.addAdjacentNode(gameBoard[xCordinate + 1][yCordinate]);
        }

        // RIGHT
        if (yCordinate < (columns - 1)) {
            node.addAdjacentNode(gameBoard[xCordinate][yCordinate + 1]);
        }

        // TOP LEFT DIAGONAL
        if (xCordinate > 0 && yCordinate > 0) {
            node.addAdjacentNode(gameBoard[xCordinate - 1][yCordinate - 1]);
        }

        // TOP RIGHT DIAGONAL
        if (xCordinate > 0 && yCordinate < (columns - 1)) {
            node.addAdjacentNode(gameBoard[xCordinate - 1][yCordinate + 1]);
        }

        // BOTTOM RIGHT DIAGONAL
        if (xCordinate < (rows - 1) && yCordinate < (columns - 1)) {
            node.addAdjacentNode(gameBoard[xCordinate + 1][yCordinate + 1]);
        }


        // BOTTOM LEFT DIAGONAL
        if (xCordinate < (rows - 1) && yCordinate > 0) {
            node.addAdjacentNode(gameBoard[xCordinate + 1][yCordinate - 1]);
        }

        gameBoard[xCordinate][yCordinate] = node;
    }

    @Override
    public void BFS(Box origin) {
        Queue<Box> queue = new LinkedList<>();
        ((LinkedList<Box>) queue).push(origin);
        while (!queue.isEmpty()) {
            Box selected = queue.poll();
            int xCoordinate = selected.getXCoordinate();
            int yCoordinate = selected.getYCoordinate();
            //gameBoard[xCoordinate][yCoordinate].visitBox();

            if (gameBoard[xCoordinate][yCoordinate].getMinesAround() == 0 && !gameBoard[xCoordinate][yCoordinate].isVisited()) {
                List<INode<String>> adjacent = selected.getAdjacentNodes();
                for (INode<String> node : adjacent) {
                    Box adjacentBox = (Box) node;
                    int adjacentXCoordinate = adjacentBox.getXCoordinate();
                    int adjacentYCoordinate = adjacentBox.getYCoordinate();
                    gameBoard[adjacentXCoordinate][adjacentYCoordinate].visitBox();
                    ((LinkedList<Box>) queue).push(adjacentBox);
                }
            }
        }
    }

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

    private void fillBoardWithMines() {
        int minesLeft = mines;
        Random random = new Random();

        while (minesLeft > 0) {
            int xCordinate = random.nextInt(Integer.MAX_VALUE) % rows;
            int yCordinate = random.nextInt(Integer.MAX_VALUE) % columns;

            if (!gameBoard[xCordinate][yCordinate].isMine()) {
                gameBoard[xCordinate][yCordinate].setType(Box.MINE_CHAR);
                gameBoard[xCordinate][yCordinate].setMine(true);
                System.out.println(xCordinate + " - " + yCordinate);
                minesLeft--;
            }
        }
    }

    private int numberOfMinesAround(int i, int j) {
        Box selectedBox = gameBoard[i][j];
        List<INode<String>> adjacent = selectedBox.getAdjacentNodes();
        int minesAround = 0;
        for (INode<String> node : adjacent) {
            Box box = (Box) node;
            if (box.isMine()) {
                minesAround++;
            }
        }
        return minesAround;
    }

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

    public void revealMines() {
        printBoard(gameBoard, true);
    }

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

    public void displayGameBoard() {
        printBoard(gameBoard, false);
    }

    public boolean getLost() {
        return lost;
    }

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

    public void openBox(int row, int column) throws WrongInputException {
        Box selectedBox = gameBoard[row][column];
        if (selectedBox != null) {
            if (!selectedBox.isVisited()) {
                gameBoard[row][column].visitBox();
                if (selectedBox.isMine()) {
                    this.lost = true;
                } else {
                    BFS(selectedBox);
                }
            }
        } else {
            throw new WrongInputException("Invalid row or column");
        }
    }

    public void solveGame() {
        Box[][] solvedBoard = gameBoard;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                solvedBoard[i][j].visitBox();
            }
        }
        printBoard(solvedBoard, true);
    }
}
