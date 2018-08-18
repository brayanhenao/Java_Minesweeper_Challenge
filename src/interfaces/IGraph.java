/**
 * PSL Challenge - Minesweeper Game
 * Universidad Icesi
 *
 * @author Brayan Henao
 * @version 1.0
 */
package interfaces;

public interface IGraph<T> {

    // METHODS

    /**
     * Method to get the number of rows in a graph.
     *
     * @return int - the number of rows in the graph.
     */
    int getRows();

    /**
     * Method to get the number of columns in a graph.
     *
     * @return int - the number of columns in the graph.
     */
    int getColumns();

    /**
     * Method to add a node to the graph.
     *
     * @param node - the node to be added.
     */
    void addNode(T node);

    /**
     * Method to Breadth first search (traversal) in a graph.
     *
     * @param origin - the origin node of the traversal.
     */
    void BreadthFirstSearch(T origin);

}
