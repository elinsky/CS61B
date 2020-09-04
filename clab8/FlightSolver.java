import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    PriorityQueue<Flight> pq_start, pq_end;
    int max;

    public FlightSolver(ArrayList<Flight> flights) {
        max = 0;
        int in_flight = 0;
        pq_start = new PriorityQueue<>(flights.size(), new Flight.StartOrder());
        pq_end = new PriorityQueue<>(flights.size(), new Flight.EndOrder());
        pq_start.addAll(flights);
        pq_end.addAll(flights);

        while (!pq_start.isEmpty() | !pq_end.isEmpty()) {
            if (!pq_start.isEmpty() & !pq_end.isEmpty()) {
                Flight next_start = pq_start.peek();
                Flight next_end = pq_end.peek();
                if (next_start.startTime() <= next_end.endTime()) {
                    in_flight += pq_start.poll().passengers();
                } else {
                    in_flight -= pq_end.poll().passengers();
                }
            } else if (!pq_start.isEmpty() & pq_end.isEmpty()) {
                in_flight += pq_start.poll().passengers();
            } else {
                in_flight -= pq_end.poll().passengers();
            }
            if (in_flight > max) {
                max = in_flight;
            }
        }
    }

    public int solve() {
        return max;
    }

}
