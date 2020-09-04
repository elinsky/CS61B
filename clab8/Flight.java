import java.util.Comparator;

/**
 * Represents a flight in the Flight problem.
 */
public class Flight {

    int startTime;
    int endTime;
    int passengers;

    public Flight(int start, int end, int numPassengers) {
        startTime = start;
        endTime = end;
        passengers = numPassengers;
    }

    public int startTime() {
        return startTime;
    }

    public int endTime() {
        return endTime;
    }

    public int passengers() {
        return passengers;
    }

    public static class StartOrder implements Comparator<Flight> {
        public int compare(Flight v, Flight w) {
            return Integer.compare(v.startTime(), w.startTime());
        }
    }

    public static class EndOrder implements Comparator<Flight> {
        public int compare(Flight v, Flight w) {
            return Integer.compare(v.endTime(), w.endTime());
        }
    }
}
