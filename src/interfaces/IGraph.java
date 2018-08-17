package interfaces;

public interface IGraph<T> {


    public int getRows();

    public int getColumns();

    public void addNode(T node);

    public void BFS(T origin);

}
