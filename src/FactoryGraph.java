import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kiuny on 16.03.2017.
 * Factory Class for a Graphs
 */

public class FactoryGraph {

    public static int threads = 10;

    public static Graph<Integer> randomGraph(int vertices, int edges) {
        Graph<Integer> g = new Graph<>();
        CostMap<Integer, Integer> cm = new CostMap<>();
        g.attachCostMap(cm);

        for (Integer i = 0; i < vertices; i++) {
            g.newVertex(i);
        }

        class EdgeCreatorRunnable implements Runnable {
            private int count;
            private Random rng;

            public EdgeCreatorRunnable(int count) {
                this.count = count;
                this.rng = new Random();
            }

            @Override
            public void run() {
                while (count > 0) {
                    int s = this.rng.nextInt(vertices);
                    int d = this.rng.nextInt(vertices);

                    if (!g.checkEdge(s, d) && s != d) {
                        count--;
                        g.addEdge(s, d, rng.nextInt(200));
                    }
                }
            }
        }
        ExecutorService es = Executors.newFixedThreadPool(threads);
        int left = edges;
        while (left > 0) {
            es.execute(new EdgeCreatorRunnable(left > edges / threads ? edges / threads : left));
            left -= edges / threads;
        }

        es.shutdown();
        while (true) {
//            System.out.print("aaa");
//            es.
            try {
                if (es.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                    return g;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Graph<Integer> fromFileWithCostMap(String filename) throws IOException {
        Graph<Integer> g = new Graph<>();
        g.attachCostMap(new CostMap<Integer, Integer>());

        BufferedReader br = new BufferedReader(new FileReader(filename));
        Scanner sc = new Scanner(br);

        int vertices = sc.nextInt();
        int edges = sc.nextInt();


        for (int i = 0; i < vertices; i++) {
            g.newVertex(i);
        }
        int l, r, c;
        for (int i = 0; i < edges; i++) {
            l = sc.nextInt();
            r = sc.nextInt();
            c = sc.nextInt();

            g.addEdge(l, r, c);
        }

        br.close();
        sc.close();
        return g;
    }

    public static Graph<Integer> fromFile(String filename) throws IOException {
        Graph<Integer> g = new Graph<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        Scanner sc = new Scanner(br);

        int vertices = sc.nextInt();
        int edges = sc.nextInt();
        for (int i = 0; i < vertices; i++) {
            g.newVertex(i);
        }

        int l, r;
        for (int i = 0; i < edges; i++) {
            l = sc.nextInt();
            r = sc.nextInt();

            g.addEdge(l, r);
        }
        br.close();
        sc.close();
        return g;
    }

}



















