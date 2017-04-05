import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by kiuny on 28.03.2017.
 * Console runner
 */
public class Console implements Runnable {

    Scanner input;
    private Graph<Integer> activeGraph;
    private HashMap<String, Runnable> actions;
    private boolean running;

    public Console() {
        actions = new HashMap<>();
        running = true;
        input = new Scanner(System.in);
        this.activeGraph = new Graph<>();
        actions.put("addv", this::addv);
        actions.put("remv", this::remv);
        actions.put("adde", this::adde);
        actions.put("reme", this::reme);
        actions.put("addc", this::addc);
        actions.put("remc", this::remc);
        actions.put("show", this::show);
        actions.put("rand", this::rand);
        actions.put("randc", this::randc);
        actions.put("read", this::read);
        actions.put("readc", this::readc);
        actions.put("save", this::save);
        actions.put("exit", this::exit);
        actions.put("help", this::printOptions);
    }

    void printOptions() {
        System.out.println("addv: Adds a vertex to the current graph");
        System.out.println("remv: Remove a vertex from the current graph");
        System.out.println("adde: Adds an edge to the current graph");
        System.out.println("reme: Remove an endge from the current graph");
        System.out.println("addc: Attaches a costmap");
        System.out.println("remc: Removes the costmap");
        System.out.println();
        System.out.println("show: shows current graph");
        System.out.println("rand: generates a random graph. Replaces current.");
        System.out.println("randc: generates a random graph with costs. Replaces current.");
        System.out.println();
        System.out.println("read: Reads a custom graph");
        System.out.println("readc: Reads a custom graph with costs");
        System.out.println("save: Save the current graph");
        System.out.println();
        System.out.println("exit.");
    }

    private void save() {
        System.out.print("Filename: ");
        String filename = input.next();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(this.activeGraph.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println("Cannot write");
        }


    }

    private void readc() {
        System.out.print("Filename: ");
        String filename = input.next();

        try {
            this.activeGraph = FactoryGraph.fromFileWithCostMap(filename);
        } catch (IOException e) {

            System.out.println("File does not exist");

        }
    }

    private void read() {
        System.out.print("Filename: ");
        String filename = input.next();

        try {
            this.activeGraph = FactoryGraph.fromFile(filename);
        } catch (IOException e) {
            System.out.println("File does not exist");
        }
    }

    private void addv() {
        System.out.print("Id: ");
        Integer id = this.input.nextInt();
        this.activeGraph.newVertex(id);
    }

    private void adde() {
        System.out.print("From: ");
        Integer from = this.input.nextInt();
        System.out.print("To: ");
        Integer to = this.input.nextInt();

        if (this.activeGraph.hasCostMap()) {
            System.out.print("Cost: ");
            Integer cost = this.input.nextInt();
            this.activeGraph.addEdge(from, to, cost);
            return;
        }

        this.activeGraph.addEdge(from, to);
    }

    private void remv() {
        System.out.print("Id: ");
        Integer id = this.input.nextInt();
        try {

            this.activeGraph.removeVertex(id);
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
        }

    }

    private void reme() {
        System.out.print("From: ");
        Integer from = this.input.nextInt();
        System.out.print("To: ");
        Integer to = this.input.nextInt();

        try {
            this.activeGraph.removeEdge(from, to);
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
        }
    }

    private void addc() {
        this.activeGraph.attachCostMap(new CostMap<Integer, Integer>());
    }

    private void remc() {
        this.activeGraph.removeCostMap();
    }

    private void show() {
        System.out.print(this.activeGraph.toString());
    }

    private void randc() {
        System.out.print("Vertices count: ");
        Integer vertices = input.nextInt();
        System.out.print("Edges count: ");
        Integer edges = input.nextInt();
        this.activeGraph = FactoryGraph.randomGraph(vertices, edges);
    }

    private void rand() {
        randc();
        this.activeGraph.removeCostMap();
    }

    private void exit() {
        this.running = false;
        input.close();
        System.out.println("Bye");
    }

    private void invalid() {
        System.out.println("Invalid command try again");
    }

    private Runnable getOption() {
        System.out.print(">>> ");
        String s = this.input.next();
        return this.actions.containsKey(s) ? this.actions.get(s) : this::invalid;
    }

    @Override
    public void run() {
        while (running) {
//            this.printOptions();
            this.getOption().run();
        }
    }
}
