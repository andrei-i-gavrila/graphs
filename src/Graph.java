import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by kiuny on 02.03.2017.
 * Base class for Directed Graph
 */
public class Graph<T> implements Cloneable {

    private HashMap<T, Vertex<T>> vertices;
    private CostMap costMap = null;

    public Graph() {
        this.vertices = new HashMap<>();
    }

    public Graph(CostMap costMap) {
        this.vertices = new HashMap<>();
        this.costMap = costMap;
    }

    public Graph(Graph<T> g) {
        g.vertices.forEach((T t, Vertex<T> v) -> this.newVertex(t));

        g.vertices.forEach((T t, Vertex<T> v) -> v.getOutVertices().forEachRemaining((Vertex<T> linked) -> {
            this.addEdge(t, linked.getId());
        }));

        if (g.hasCostMap()) {
            this.attachCostMap(new CostMap(g.costMap));
        }
    }

    public Iterator<Vertex<T>> getVertices() {
        return this.vertices.values().iterator();
    }

    public Vertex<T> getVertex(T id) {
        if (this.vertices.containsKey(id))
            return this.vertices.get(id);
        throw new NullPointerException("Vertex inexistent");
    }

    public void newVertex(T id) {
        this.vertices.put(id, new Vertex<T>(id));
    }

    public void addEdge(T start, T end) {
        Vertex<T> s = getVertex(start);
        Vertex<T> d = getVertex(end);

        s.addEdgeTo(d);
        d.addEdgeFrom(s);
    }

    public synchronized void addEdge(T start, T end, Object v) throws NullPointerException {
        if (!this.hasCostMap()) {
            throw new NullPointerException("No cost map attached");
        }
        this.addEdge(start, end);
        //noinspection unchecked
        this.costMap.set(start, end, v);
    }

    public synchronized void removeEdge(T start, T end) {
        if (this.checkEdge(start, end)) {
            throw new NullPointerException("Edge is not existent");
        }
        if (this.hasCostMap()) {
            //noinspection unchecked
            this.costMap.remove(start, end);
        }
        this.getVertex(start).removeEdgeTo(this.getVertex(end));
    }

    public synchronized void removeVertex(T vertex) {
        if (this.vertices.containsKey(vertex)) {
            Vertex<T> cv = this.getVertex(vertex);
            cv.forEachOut((v) -> {
                this.costMap.remove(vertex, v.getId());
                v.removeEdgeFrom(cv);
            });

            cv.forEachIn((v) -> {
                this.costMap.remove(v.getId(), vertex);
                v.removeEdgeTo(cv);
            });
            this.vertices.remove(vertex);
        } else {
            throw new NullPointerException("Vertex unexistent");
        }

    }

    public synchronized boolean checkEdge(T start, T end) {
        return getVertex(start).goesTo(getVertex(end));
    }

    public int getVertexCount() {
        return this.vertices.size();
    }

    public boolean hasCostMap() {
        return costMap != null;
    }

    public void attachCostMap(CostMap cmap) {
        this.costMap = cmap;
    }

    public void removeCostMap() {
        this.costMap = null;
    }

    public CostMap getCostMap() throws NullPointerException {
        return this.costMap;
    }

    @Override
    public String toString() {
        return this.hasCostMap() ? this.toStringCostMap() : this.toStringDefault();
    }

    private String toStringCostMap() {

        return String.valueOf(this.vertices.size()) + " " +
                this.costMap.size() + "\n" +
                this.costMap.toString();
    }


    private String toStringDefault() {

        StringBuilder sb = new StringBuilder();
        final int[] edges = {0};
        this.vertices.forEach((t, v) -> {
            v.getOutVertices().forEachRemaining((ver) -> {
                sb.append(v.getId()).append(" ").append(ver.getId()).append("\n");
                edges[0]++;
            });
        });

        return String.valueOf(this.vertices.size()) + " " + edges[0] + "\n" + sb.toString();
    }

}
