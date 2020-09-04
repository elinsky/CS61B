package bearmaps.hw4;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import bearmaps.hw4.project2ab.ExtrinsicMinPQ;
import bearmaps.hw4.project2ab.ArrayHeapMinPQ;


public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private int numStates;
    private ArrayList<Double> distTo;
    private HashMap<Vertex, Integer> distToIndex;
    private int distToCounter = 0;
    private ExtrinsicMinPQ<Vertex> pq;
    private AStarGraph<Vertex> graph;
    private Vertex goal;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        // TODO - make sure all the smaller methods work.  I need to set some instance variables
        Stopwatch sw = new Stopwatch();
        distTo = new ArrayList<>();
        distToIndex = new HashMap<>();
        graph = input;
        goal = end;
        pq = new ArrayHeapMinPQ<Vertex>();


        setDistTo(start, 0.0);
        pq.add(start, getDistTo(start) + graph.estimatedDistanceToGoal(start, goal));

        while (pq.size() > 0 && !pq.getSmallest().equals(goal) && sw.elapsedTime() < timeout) {
            Vertex p = pq.removeSmallest();
            numStates++;
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(p);
            for (WeightedEdge<Vertex> edge : neighborEdges) {
                relax(edge);
            }
        }
        timeSpent = sw.elapsedTime();

        if (timeSpent >= timeout) {
            outcome = SolverOutcome.TIMEOUT;
        } else if (pq.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else {
            outcome = SolverOutcome.SOLVED;
        }
    }

    private double getDistTo(Vertex v) {
        if (!distToIndex.containsKey(v)) {
            return Double.POSITIVE_INFINITY;
        }
        return distTo.get(distToIndex.get(v));
    }

    private void setDistTo(Vertex v, double dist) {
        if (!distToIndex.containsKey(v)) {
            distToIndex.put(v, distToCounter++);
        }
        distTo.set(distToIndex.get(v), dist);
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if ((getDistTo(p) + w) < getDistTo(q)) {
            setDistTo(q, getDistTo(p) + w);
            if (pq.contains(q)) {
                pq.changePriority(q, getDistTo(q) + graph.estimatedDistanceToGoal(q, goal));
            } else {
                pq.add(q, getDistTo(q) + graph.estimatedDistanceToGoal(q, goal));
            }
        }


    }

    /**
     * Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT, or
     * SolverOutcome.UNSOLVABLE.  Should be SOLVED if the AStarSolver
     * was able to complete all work in the time given.  UNSOLVABLE if the
     * priority queue became empty.  TIMEOUT if the solver ran out of time.
     */
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    /**
    Returns a list of vertices corresponding to a solution.  Should be
    empty if outcome was TIMEOUT or UNSOLVABLE.
     */
    @Override
    public List<Vertex> solution() {
        // TODO
        return solution;
    }

    /**
     * The total weight of the given solution, taking into account edge weights.
     * Should be 0 if results was TIMEOUT or UNSOLVABLE.
     */
    @Override
    public double solutionWeight() {
        // TODO
        return solutionWeight;
    }

    /**
     * The total number of priority queue dequeue operations.
     */
    @Override
    public int numStatesExplored() {
        return numStates;
    }

    /**
     * The total time spent in seconds by the constructor.
     */
    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
