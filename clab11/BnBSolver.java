import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    public List<Bear> bear_solution;
    public List<Bed> bed_solution;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        bear_solution = bears;
        bed_solution = new ArrayList<>();
        for (Bear bear : bears) {
            for (Bed bed : beds) {
                if (bed.compareTo(bear) == 0) {
                    bed_solution.add(bed);
                }
            }
        }
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return bear_solution;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return bed_solution;
    }
}
