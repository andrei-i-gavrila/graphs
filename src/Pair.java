import java.util.Objects;

/**
 * Created by kiuny on 16.03.2017.
 * Pair class with hash and equals
 */
public class Pair<T> implements Cloneable {

    private T a, b;

    public Pair(T source, T destination) {
        this.a = source;
        this.b = destination;
    }

    public T source() {
        return this.a;
    }

    public T destination() {
        return this.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    public boolean equals(Object other) {
        return other instanceof Pair && ((Pair) other).a.equals(this.a) && ((Pair) other).b.equals(this.b);
    }
}
