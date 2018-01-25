import java.util.*;

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
        return edgeExists(source , destination) ? adjacencyList.get(source).get(destination) : -1;
    }

    public boolean edgeExists(T source, T destination) {

        boolean edgeExists = false;

        // If either the source or the destination vertex don't exist then by deduction an edge can not exist
        if (vertexExists(source) && vertexExists(destination))
        {
            edgeExists = adjacencyList.get(source).containsKey(destination);
        }

        // vertexExists(source) && vertexExists(destination) && adjacencyList.get(source).containsKey(destination)
        return edgeExists;
    }

    public void addVertex(T vertex) throws IllegalArgumentException {
        if (vertex != null)
        {
            adjacencyList.put(vertex, new HashMap<>());
        }
    }

    public void removeVertex(T vertex) throws NoSuchElementException {
        if (vertexExists(vertex))
        {
            for (T vert : adjacencyList.keySet())
            {
                adjacencyList.get(vert).remove(vertex);
            }
            adjacencyList.remove(vertex);
            /*
            Iterator it = adjacencyList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                it.remove();
            }
            */
        }
    }

    public void addEdge(T source, T destination, int weight)
            throws IllegalArgumentException, NoSuchElementException {

        // Check that the vertices exist
        if (!(vertexExists(source) && vertexExists(destination))) {
            throw new NoSuchElementException();
        }

        adjacencyList.get(source).put(destination, weight);

        if (!_isDirected) {
            adjacencyList.get(destination).put(source, weight);
        }
    }

    public void removeEdge(T source, T destination) throws
            NoSuchElementException {
        if (!edgeExists(source, destination))
        {
            throw new NoSuchElementException();
        }
        adjacencyList.get(source).remove(destination);
    }

    public String toString() {
        return null;
    }

    private boolean vertexExists(T vertex) {
        return adjacencyList.containsKey(vertex);
    }
}