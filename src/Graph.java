import java.util.*;

/**
 * A class for manipulating a Graph object.
 *
 * @param <T>
 */
public class Graph<T> {

    private boolean _isDirected;

    private HashMap<T, HashMap<T, Integer>> adjacencyList;

    /**
     * Constructs a Graph object.
     *
     * @param isDirected the type of graph
     */
    public Graph(boolean isDirected) {
        _isDirected = isDirected;
        adjacencyList = new HashMap<>();
    }

    /**
     * Returns a list of vertices within the graph
     *
     * @return a list of vertices within the graph
     */
    public List<T> getVertices() {
        return new ArrayList<T>(adjacencyList.keySet());
    }

    /**
     * Returns the weight of the specified edge
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @return the weight of the specified edge
     */
    public int getEdgeWeight(T source, T destination) {

        // If the edge exits return the weight otherwise return -1
        return edgeExists(source, destination) ? adjacencyList.get(source)
                .get(destination) : -1;
    }

    /**
     * Determines if an edge exists.
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @return true if an edge exists, false if does not.
     */
    public boolean edgeExists(T source, T destination) {

        boolean edgeExists = false;

        // If either the source or the destination vertex don't exist then by
        // deduction an edge can not exist
        if (vertexExists(source) && vertexExists(destination)) {
            edgeExists = adjacencyList.get(source).containsKey(destination);
        }

        return edgeExists;
    }

    /**
     * Adds a vertex to the graph
     *
     * @param vertex the vertex to add to the graph
     * @throws IllegalArgumentException if vertex is a null value or the vertex
     * already exists
     */
    public void addVertex(T vertex) throws IllegalArgumentException {
        if (vertex == null || vertexExists(vertex)) {
            throw new IllegalArgumentException();
        }
        adjacencyList.put(vertex, new HashMap<>());
    }

    /**
     * Removes the specified vertex from the graph
     *
     * @param vertex the vertex to remove from the graph
     * @throws NoSuchElementException if the vertex to be removed does not exist.
     */
    public void removeVertex(T vertex) throws NoSuchElementException {

        Set<T> verticesToRemove;

        if (!vertexExists(vertex)) {
            throw new NoSuchElementException();
        }

        // If the graph is directed then we must iterate through every vertex
        // and remove all connected vertices
        // Else the graph is undirected
        verticesToRemove = _isDirected ? adjacencyList.keySet() :
                adjacencyList.get(vertex).keySet();

        for (T connectVertex : verticesToRemove) {
            adjacencyList.get(connectVertex).remove(vertex);
        }

        adjacencyList.remove(vertex);

    }

    /**
     * Adds an edge to the graph
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param weight      the weight of the edge
     * @throws IllegalArgumentException if the source or destination vertices
     * are equal
     * @throws NoSuchElementException   if the source or destination vertices
     * don't exist
     */
    public void addEdge(T source, T destination, int weight)
            throws IllegalArgumentException, NoSuchElementException {

        // Check that the vertices exist
        if (!(vertexExists(source) && vertexExists(destination))) {
            throw new NoSuchElementException();
        }

        // Check that the vertices are not the same
        if (source.equals(destination)) {
            throw new IllegalArgumentException();
        }

        adjacencyList.get(source).put(destination, weight);

        if (!_isDirected) {
            adjacencyList.get(destination).put(source, weight);
        }
    }

    /**
     * Removes an edge from the graph
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @throws NoSuchElementException if the edge does not exist
     */
    public void removeEdge(T source, T destination) throws
            NoSuchElementException {

        if (!edgeExists(source, destination)) {
            throw new NoSuchElementException();
        }

        adjacencyList.get(source).remove(destination);

        if (!_isDirected) {
            adjacencyList.get(destination).remove(source);
        }
    }

    /**
     * Returns the string representation of the graph
     *
     * @return the string representation of the graph
     */
    public String toString() {
        Set<T> vertices =  adjacencyList.keySet();
        StringBuilder stringBuilder = new StringBuilder("Vertices: ");
        stringBuilder.append(Arrays.toString(vertices.toArray()));
        stringBuilder.append("\nSource -> Destination (Weight)\n");
        for (T sourceVertex : adjacencyList.keySet()) {
            for (T destinationVertex : adjacencyList.get(sourceVertex).keySet()){
                stringBuilder.append(sourceVertex.toString()).append(" -> ")
                        .append(destinationVertex.toString()).append(' ')
                        .append("( ")
                        .append(adjacencyList.get(sourceVertex)
                                .get(destinationVertex)).append(" )\n");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Helper method to check if a vertex exists (for readabilities sake)
     *
     * @param vertex the vertex whose existence is in question
     * @return true if the vertex exits and false if it does not
     */
    private boolean vertexExists(T vertex) {
        return adjacencyList.containsKey(vertex);
    }
}