/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        int min_depth = (int) Math.floor(Math.log(N) / Math.log(2));
        int ipl = 0;
        for (int depth = 0; depth <= min_depth; depth++) {
            int last_node = (int) Math.pow(2, depth);
            for (int node_pos = 0; node_pos < last_node && N > 0; node_pos++) {
                ipl = ipl + depth;
                N--;
            }
        }
        return ipl;
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        return (double) optimalIPL(N) / (double) N;
    }
}
