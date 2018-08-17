package model;

import exceptions.WrongInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {

    private final static char MARK_CHAR = 'M';
    private final static char UNCOVER_CHAR = 'U';

    private BufferedReader reader;
    private Minesweeper minesweeper;
    private String line;
    private boolean gameFinish;

    public Game() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        gameFinish = false;
        int rows, columns, mines;
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
                    minesweeper.displayGameBoard();
                    minesweeper.solveGame();
                    playerInteractions();
                    reader.close();
                } catch (NumberFormatException e) {
                    System.out.println("Error: only numbers allowed");
                }
            } catch (IOException e) {
                System.out.println("Error: wrong input format");
            }
        } while (!gameFinish);
    }

    public void playerInteractions() throws IOException {
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

    public static void main(String[] args) throws IOException {
        Game mineSweeperGame = new Game();
    }
}
