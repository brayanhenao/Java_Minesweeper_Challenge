/**
 * PSL Challenge - Minesweeper Game
 * Universidad Icesi
 *
 * @author Brayan Henao
 * @version 1.0
 */

package model;

import java.util.ArrayList;
import java.util.List;

public class Box {

    // CONSTANTS

    /**
     * Constant used to represent that the box is a mine type.
     */
    public final static char MINE_CHAR = '*';

    /**
     * Constant used to represent that the box disabled type.
     */
    public final static char DISABLE_CELL_CHAR = '-';

    /**
     * Constant used to represent that the box is unselected type.
     */
    public final static char UNSELECTED_CELL_CHAR = '.';

    /**
     * Constant used to represent that the box is a flag type.
     */
    public final static char FLAG_CHAR = 'P';

    // ATTRIBUTES

    /**
     * Number of mines around the box.
     */
    private int minesAround;

    /**
     * Represent if the box is already visited.
     */
    private boolean visited;

    /**
     * The x coordinate of the box (row index).
     */
    private int xCoordinate;

    /**
     * The y coordinate of the box (column index).
     */
    private int yCoordinate;

    /**
     * List with all the adjacent boxes (in every direction).
     */
    private List<Box> adjacentBoxes;

    /**
     * Represent the type of the box.
     */
    private char type;

    /**
     * Represent if the box is a mine.
     */
    private boolean mine;

    /**
     * Constructor of the Box class.
     *
     * @param xCoordinate - the x coordinate of the box.
     * @param yCoordinate - the y coordinate of the box.
     * @param type        - the box type.
     */
    public Box(int xCoordinate, int yCoordinate, char type) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.type = type;
        this.minesAround = 0;
        this.visited = false;
        adjacentBoxes = new ArrayList<>();
    }

    // METHODS


    /**
     * Method to get the x coordinate of the box.
     *
     * @return int - the x coordinate of the box.
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Method to get the x coordinate of the box.
     *
     * @return int - the x coordinate of the box.
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * Method to check if the box is visited.
     *
     * @return boolean - the visited attribute of the box.
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Method to get the value of the box (could be the box type or the mines around the box).
     *
     * @return String - the value of the box.
     */
    public String getValue() {
        if (!isVisited()) {
            if (isMine() && !isFlag()) {
                return UNSELECTED_CELL_CHAR + "";
            }
            return type + "";
        } else {
            if (minesAround == 0) {
                return DISABLE_CELL_CHAR + "";
            } else {
                return minesAround + "";
            }

        }
    }

    /**
     * Method to get the adjacent boxes of the box.
     *
     * @return List - the list with all the adjacent boxes.
     */

    public List<Box> getAdjacentBoxes() {
        return adjacentBoxes;
    }

    /**
     * Method to add an adjacent box to the adjacent list of a box.
     *
     * @param adjacent - the adjacent box.
     */
    public void addAdjacentBox(Box adjacent) {
        this.adjacentBoxes.add(adjacent);
    }

    /**
     * Method to check if the box is a mine.
     *
     * @return boolean - the the box is a mine.
     */
    public boolean isMine() {
        return mine;
    }

    /**
     * Method to check if the box is a flag.
     *
     * @return boolean - the the box is a flag.
     */
    public boolean isFlag() {
        return type == FLAG_CHAR;
    }

    /**
     * Method to visit a box.
     */
    public void visitBox() {
        this.visited = true;
        if (minesAround == 0 && type != MINE_CHAR) {
            this.type = DISABLE_CELL_CHAR;
        }
    }

    /**
     * Method to mark a box as a flag.
     */
    public void flagBox() {
        this.type = FLAG_CHAR;
    }

    /**
     * Method to get the number of mines around of a box.
     *
     * @return int - the number of mines around
     */
    public int getMinesAround() {
        return minesAround;
    }

    /**
     * Method to set the number of mines around of a box.
     *
     * @param minesAround - the number of mines around.
     */
    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    /**
     * Method to set the type of a box.
     *
     * @param type- the type of the box.
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     * Method to set if a box is mine or not.
     *
     * @param isMine- the box is a mine or not.
     */
    public void setMine(boolean isMine) {
        this.mine = isMine;
    }
}
