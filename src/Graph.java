import java.io.IOException;
import java.util.*;

/**
 * A class for manipulating a Graph object.
 *
 * @param <T>
 */
public class Graph<T> {

    private static final int NO_EDGE = -1;
    private boolean _isDirected;
    private HashMap<T, HashMap<T, Integer>> _adjacencyList;


    /**
     * Constructs a Graph object.
     *
     * @param isDirected the type of graph
     */
    public Graph(boolean isDirected) {
        _isDirected = isDirected;
        _adjacencyList = new HashMap<>();
    }


    /**
     * Constructs a graph from the specified csv file which must be in the
     * following format:
     * <p>
     * <# of vertices>
     * <first vertex>
     * <second vertex>
     * ...
     * <nth vertex>
     * <# of edges>
     * <source>,<destination>,<weight>
     * <source>,<destination>,<weight>
     * ...
     * </p>
     *
     * @param isDirected a boolean that indicates if the graph is directed
     * @param inputFile  the file from which to read in the graph data
     * @return a {@code Graph} object
     * @throws IOException if there is a problem with the file
     */
    public static Graph<String> fromCSVFile(boolean isDirected,
                                            Scanner inputFile)
            throws IOException {

        int numEdges;
        Graph<String> graph = new Graph<>(isDirected);
        int numVertices = inputFile.nextInt();

        for (int i = 0; i < numVertices + 1; i++) {
            graph.addVertex(inputFile.nextLine());
        }

        numEdges = inputFile.nextInt();

        // Set the delimiter to denominate for a carriage return or comma
        inputFile.useDelimiter("[,\r]");

        for (int i = 0; i < numEdges; i++) {
            graph.addEdge(inputFile.next().trim(), inputFile.next().trim(),
                    inputFile.nextInt());
        }

        return graph;
    }


    /**
     * Returns a list of vertices within the graph
     *
     * @return a list of vertices within the graph
     */
    public List<T> getVertices() {
        return new ArrayList<T>(_adjacencyList.keySet());
    }


    /**
     * Returns the weight of the specified edge
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @return the weight of the specified edge
     */
    public int getEdgeWeight(T source, T destination) {

        int edgeWeight = NO_EDGE;

        // If the edge exits return the weight otherwise return -1
        if (edgeExists(source, destination)) {
            edgeWeight = _adjacencyList.get(source).get(destination);
        }

        return edgeWeight;
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
            edgeExists = _adjacencyList.get(source).containsKey(destination);
        }

        return edgeExists;
    }


    /**
     * Adds a vertex to the graph
     *
     * @param vertex the vertex to add to the graph
     * @throws IllegalArgumentException if vertex is a null value or the vertex
     *                                  already exists
     */
    public void addVertex(T vertex) throws IllegalArgumentException {
        if (vertex == null || vertexExists(vertex)) {
            throw new IllegalArgumentException();
        }
        _adjacencyList.put(vertex, new HashMap<>());
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
        verticesToRemove = _isDirected ? _adjacencyList.keySet() :
                _adjacencyList.get(vertex).keySet();

        for (T connectVertex : verticesToRemove) {
            _adjacencyList.get(connectVertex).remove(vertex);
        }

        _adjacencyList.remove(vertex);

    }


    /**
     * Adds an edge to the graph
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param weight      the weight of the edge
     * @throws IllegalArgumentException if the source or destination vertices
     *                                  are equal
     * @throws NoSuchElementException   if the source or destination vertices
     *                                  don't exist
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

        _adjacencyList.get(source).put(destination, weight);

        if (!_isDirected) {
            _adjacencyList.get(destination).put(source, weight);
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

        _adjacencyList.get(source).remove(destination);

        if (!_isDirected) {
            _adjacencyList.get(destination).remove(source);
        }
    }


    /**
     * Returns the string representation of the graph
     *
     * @return the string representation of the graph
     */
    public String toString() {
        return "_isDirected: " + _isDirected + "\n" + _adjacencyList.toString();
    }


    /**
     * Returns the length of the specified path or -1 if the sequence is not a
     * path or the list is empty.
     *
     * @param path the path whose length is in question
     * @return the length of the specified path or -1 if the sequence is not a
     * path or the list is empty.
     */
    public long pathLength(List<T> path) {
        int pathLength = -1;
        boolean edgeExists = true;

        T source;
        T destination;

        if (path.size() > 1) {
            source = path.get(0);
            destination = path.get(1);

            if (edgeExists(source, destination)) {

                pathLength = _adjacencyList.get(path.get(0)).get(path.get(1));

                for (int i = 2; i < path.size() && edgeExists(source,
                        destination); i++) {

                    source = destination;
                    destination = path.get(i);

                    pathLength += _adjacencyList.get(source).get(destination);
                }
            }
        }

        return pathLength;
    }


    /**
     * Finds the shortest path between the specified two vertices.
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @return the list of vertices that form the shortest path or null if no
     * path exists.
     * @throws NoSuchElementException if one or more of vertices don't exist.
     */
    public List<Edge<T>> shortestPathBetween(T source, T destination)
            throws NoSuchElementException {

        if (!(vertexExists(source) && vertexExists(destination))) {
            throw new NoSuchElementException();
        }

        PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<>();

        boolean done = false;

        HashMap<T, Integer> connectedVertices;

        HashMap<T, T> visitedVertices = new HashMap<>();

        List<Edge<T>> path = new LinkedList<>();

        int weight = 0;

        // Remove our starting vertex
        T currentVertex = source;

        Edge<T> currentEdge = null;

        // TODO: is_Directed?
        // TODO: Source and Destination are equal?
        while (!done) {
            // Get list of all vertices connected to source vertex, create edge
            // objects, and throw them into the priorityQueue

            connectedVertices = _adjacencyList.get(currentVertex);

            // TODO: Check if set is empty or not
            for (T vertex : connectedVertices.keySet()) {
                priorityQueue.add(new Edge<>(currentVertex, vertex,
                        connectedVertices.get(vertex) + weight));
            }


            currentEdge = priorityQueue.remove();

            while (visitedVertices.containsKey(
                    currentEdge.getDestination()
            )) {
                currentEdge =
                        priorityQueue.poll();
            }

            currentVertex = currentEdge.getDestination();

            visitedVertices.put(currentEdge.getDestination(),
                    currentEdge.getSource());

            weight = currentEdge.getWeight();
            done = priorityQueue.isEmpty() || currentVertex.equals(destination);
        }

        if (currentVertex.equals(destination)) {
            while (!currentVertex.equals(source)) {
                path.add(new Edge<>(visitedVertices.get(currentVertex),
                        currentVertex, _adjacencyList.get(visitedVertices
                        .get(currentVertex)).get(currentVertex)));

                currentVertex = visitedVertices.get(currentVertex);
            }
        }

        return path;
    }


    /**
     * Helper method to check if a vertex exists (for readabilities sake)
     *
     * @param vertex the vertex whose existence is in question
     * @return true if the vertex exits and false if it does not
     */
    private boolean vertexExists(T vertex) {
        return _adjacencyList.containsKey(vertex);
    }


    /**
     * A class for managing edges of a graph.
     *
     * @param <E> the type of object
     */
    public static class Edge<E> implements Comparable<Edge<E>> {

        private int _weight;
        private E _destination;
        private E _source;


        /**
         * Constructs and Edge object
         *
         * @param source      the source vertex
         * @param destination the destination vertex
         * @param weight      the weight of the edge
         */
        public Edge(E source, E destination, int weight) {
            _source = source;
            _destination = destination;
            _weight = weight;
        }


        /**
         * Returns The weight of the edge
         *
         * @return the weight of the edge
         */
        public int getWeight() {
            return _weight;
        }


        /**
         * @return
         */
        public E getSource() {
            return _source;
        }


        /**
         * Returns the destination vertex of the edge
         *
         * @return the destination vertex of the edge
         */
        public E getDestination() {
            return _destination;
        }


        /**
         * Returns the string representation of the edge
         *
         * @return the string representation of the edge
         */
        public String toString() {
            return "source: " + _source.toString() + "destination: " +
                    _destination.toString() + "weight: " + _weight;
        }


        @Override
        public int compareTo(Edge<E> edge) {
            return Integer.compare(this._weight, edge._weight);
        }
    }
}