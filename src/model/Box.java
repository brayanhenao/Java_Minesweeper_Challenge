package model;

import interfaces.INode;

import java.util.ArrayList;
import java.util.List;

public class Box implements INode<String> {

    public final static char MINE_CHAR = '*';
    public final static char DISABLE_CELL_CHAR = '-';
    public final static char UNSELECTED_CELL_CHAR = '.';
    public final static char FLAG_CHAR = 'P';

    private int minesAround;

    private boolean visited;

    private int xCoordinate;

    private int yCoordinate;

    private List<INode<String>> adjacentBoxes;

    private char type;

    private boolean mine;

    public Box(int xCoordinate, int yCoordinate, char type) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.type = type;
        this.minesAround = 0;
        this.visited = false;
        adjacentBoxes = new ArrayList<>();
    }

    @Override
    public int getXCoordinate() {
        return xCoordinate;
    }

    @Override
    public int getYCoordinate() {
        return yCoordinate;
    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    @Override
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

    @Override
    public List<INode<String>> getAdjacentNodes() {
        return adjacentBoxes;
    }


    @Override
    public void addAdjacentNode(INode<String> adjacent) {
        this.adjacentBoxes.add(adjacent);
    }

    public boolean isMine() {
        return mine;
    }

    public boolean isFlag() {
        return type == FLAG_CHAR;
    }

    public void visitBox() {
        this.visited = true;
        if (minesAround == 0 && type != MINE_CHAR) {
            this.type = DISABLE_CELL_CHAR;
        }
    }

    public void flagBox() {
        this.type = FLAG_CHAR;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setMine(boolean isMine) {
        this.mine = isMine;
    }
}
