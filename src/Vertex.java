import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by kiuny on 02.03.2017.
 * Base vertex class
 */
class Vertex<T> {

    private T id;
    private HashSet<Vertex<T>> inLinks, outLinks;

    public Vertex(T id) {
        this.id = id;
        this.inLinks = new HashSet<>();
        this.outLinks = new HashSet<>();
    }

    public Iterator<Vertex<T>> getOutVertices() {
        return outLinks.iterator();
    }

    public void forEachOut(Consumer<Vertex<T>> con) {
        this.outLinks.forEach(con);
    }

    public Iterator<Vertex<T>> getInVertices() {
        return inLinks.iterator();
    }

    public void forEachIn(Consumer<Vertex<T>> con) {
        this.inLinks.forEach(con);
    }

    public int getInDegree() {
        return inLinks.size();
    }

    public int getOutDegree() {
        return outLinks.size();
    }

    public void addEdgeTo(Vertex<T> v) {
        this.outLinks.add(v);
    }

    public void addEdgeFrom(Vertex<T> v) {
        this.inLinks.add(v);
    }

    public void removeEdgeTo(Vertex<T> v) {
        this.outLinks.remove(v);
    }

    public void removeEdgeFrom(Vertex<T> v) {
        this.inLinks.remove(v);
    }


    public T getId() {
        return id;
    }

    public boolean goesTo(Vertex<T> target) {
        return this.outLinks.contains(target);
    }
}
