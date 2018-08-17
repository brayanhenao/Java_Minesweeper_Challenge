package interfaces;

import java.util.List;

public interface INode<T> {

    public int getXCoordinate();

    public int getYCoordinate();

    public boolean isVisited();

    public T getValue();

    public List<INode<T>> getAdjacentNodes();

    public void addAdjacentNode(INode<T> adjacent);
}
