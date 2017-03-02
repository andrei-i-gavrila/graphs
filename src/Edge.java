/**
 * Created by kiuny on 02.03.2017.
 * Base edge class
 */
public class Edge {

    private int start;
    private int end;

    public Edge(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }
}
