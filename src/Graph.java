import java.util.List;
import java.util.NoSuchElementException;

public class Graph<T> {

    public Graph(boolean isDirected) {
    }

    public List<T> getVertices() {
        return null;
    }

    public int getEdgeWeight(T source, T destination) {
        return 0;
    }

    public boolean edgeExists(T source, T destination) {
        return false;
    }

    public void addVertex(T vertex) throws IllegalArgumentException {
    }

    public void removeVertex(T vertex) throws NoSuchElementException {
    }

    public void addEdge(T source, T destination, int weight)
            throws IllegalArgumentException, NoSuchElementException {
    }

    public void removeEdge(T source, T destination) throws
            NoSuchElementException {
    }

    public String toString() {
        return null;
    }
}

//TODO: Implement using map of maps