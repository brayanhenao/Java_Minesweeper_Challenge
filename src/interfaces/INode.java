/**
 * PSL Challenge - Minesweeper Game
 * Universidad Icesi
 *
 * @author Brayan Henao
 * @version 1.0
 */
package interfaces;

import java.util.List;

public interface INode<T> {

    // METHODS

    /**
     * Method to check if the node is visited.
     *
     * @return boolean - the visited attribute of the node.
     */
    boolean isVisited();

    /**
     * Method to get the value of the node.
     *
     * @return T - the value of the node.
     */
    T getValue();

    /**
     * Method to get the adjacent nodes of the node.
     *
     * @return List - the list with all the adjacent nodes.
     */
    List<INode<T>> getAdjacentNodes();

    /**
     * Method to add an adjacent node to the adjacent list of a node.
     *
     * @param adjacent - the adjacent node.
     */
    void addAdjacentNode(INode<T> adjacent);
}
