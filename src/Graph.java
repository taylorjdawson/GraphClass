import java.util.List;
import java.util.NoSuchElementException;

public class Graph<T> {
   public Graph(boolean isDirected) {}
   public List<T> getVertices() {}
   public int getEdgeWeight(T source, T destination) {}
   public boolean edgeExists(T source, T destination) {}
   public void addVertex(T vertex) throws IllegalArgumentException {}
   public void removeVertex(T vertex) throws NoSuchElementException {}
   public void addEdge(T source, T destination, int weight)
         throws IllegalArgumentException, NoSuchElementException {}
   public void removeEdge(T source, T destination) throws
         NoSuchElementException {}
   public String toString() {}
}
//TODO: Implement using map of maps