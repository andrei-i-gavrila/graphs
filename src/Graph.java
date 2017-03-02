import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by kiuny on 02.03.2017.
 * Base class for Directed Graph
 */
public class Graph {

    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public Iterator<Vertex> getVertices() {
        return this.vertices.iterator();
    }

    public Vertex getVertex(int id) {
        return this.vertices.get(id);
    }

    public void newVertex() {
        this.vertices.add(new Vertex());
    }

    public void addEdge(int start, int end) {

    }


}
