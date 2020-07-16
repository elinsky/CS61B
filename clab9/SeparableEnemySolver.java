import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.HashMap;


public class SeparableEnemySolver {

    Graph g;

    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     */
    public boolean isSeparable() {
        Cycle cycle = new Cycle(g);
        return !cycle.hasCycle();
    }

    private static class Cycle {
        boolean hasCycle;
        boolean hasOddCycle;
        Graph g;
        HashMap<String, Boolean> marked;

        public Cycle(Graph graph) {
            this.g = graph;
            hasCycle = false;
            hasOddCycle = false;
            marked = new HashMap<String, Boolean>();
            for (String node : g.labels()) {
                marked.put(node, false);
            }
            for (String node : g.labels()) {
                if (!marked.get(node)) {
                    DFS(g, "null", node, 1);
                }
            }
        }

        private void DFS(Graph g, String from, String at, int depth) {
            marked.put(at, true);
            for (String neighbor : g.neighbors(at)) {
                if (!marked.get(neighbor)) {
                    DFS(g, at, neighbor, depth + 1);
                } else if (!from.equals(neighbor)) {
                    hasCycle = true;
                    if (depth % 2 == 1 & depth > 1) {
                        hasOddCycle = true;
                    }
                }
            }
        }

        public boolean hasCycle() {
            return hasOddCycle;
        }
    }


    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */

}
