import java.util.HashMap;

/**
 * Created by kiuny on 02.03.2017.
 * Base edge class
 */

public class CostMap<T, V> {

    private HashMap<Pair<T>, V> edges;

    public CostMap() {
        this.edges = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public CostMap(CostMap cmap) {
        this.edges = new HashMap<>(cmap.edges);
    }

    public boolean exists(T a, T b) {
        return this.edges.containsKey(new Pair<T>(a, b));
    }

    public boolean exists(Pair<T> p) {
        return this.edges.containsKey(p);
    }


    public void set(T a, T b, V v) {
        this.edges.put(new Pair<T>(a, b), v);
    }

    public void set(Pair<T> p, V v) {
        this.edges.put(p, v);
    }

    public V get(T a, T b) {
        return edges.get(new Pair<>(a, b));
    }

    public V get(Pair<T> p) {
        return edges.get(p);
    }

    public void remove(T a, T b) {
        if (this.exists(new Pair<>(a, b)))
            this.edges.remove(new Pair<>(a, b));
    }

    public void remove(Pair<T> p) {
        this.edges.remove(p);
    }

    public int size() {
        return this.edges.size();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        this.edges.forEach((p, v) -> {
            sb.append(p.source()).append(" ").append(p.destination()).append(" ").append(v).append("\n");
        });

        return sb.toString();
    }
}
