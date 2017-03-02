import java.util.ArrayList;

/**
 * Created by kiuny on 02.03.2017.
 * Base vertex class
 */
class Vertex {

    private int id;
    private ArrayList<int> inLinks;
    private ArrayList<int> outLinks;

    public Vertex() {
        inLinks = new ArrayList<int>();
        outLinks = new ArrayList<int>();
    }

    public int getInDegree() {
        return inLinks.size();
    }

    public int getOutDegree() {
        return outLinks.size();
    }
}
