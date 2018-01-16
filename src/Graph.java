import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class Graph<T> {

    private static boolean _isDirected;

    private HashMap<T, HashMap<T, Integer>> adjacencyList;

    public Graph(boolean isDirected) {
        _isDirected = isDirected;
        adjacencyList = new HashMap<>();
    }

    public List<T> getVertices() {
        return new ArrayList<T>(adjacencyList.keySet());
    }

    public int getEdgeWeight(T source, T destination) {
        return 0;
    }

    public boolean edgeExists(T source, T destination) {
        return false;
    }

    public void addVertex(T vertex) throws IllegalArgumentException {
        adjacencyList.put(vertex, new HashMap<>());
    }

    public void removeVertex(T vertex) throws NoSuchElementException {

        //TODO: Check that the vertex exists
        adjacencyList.remove(vertex);
    }

    public void addEdge(T source, T destination, int weight)
            throws IllegalArgumentException, NoSuchElementException {

        //TODO: Check that edges don't already exist

        //TODO: Check that the vertex exists
        adjacencyList.get(source).put(destination, weight);
    }

    public void removeEdge(T source, T destination) throws
            NoSuchElementException {
    }

    public String toString() {
        return null;
    }
}

//TODO: Implement using map of maps