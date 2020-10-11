package byow.Core;

import edu.princeton.cs.algs4.StdDraw;

// Make this general purpose
public class KeyListener {

    public static String get_keypress(int n) {
        StringBuilder result = new StringBuilder();
        while (result.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                result.append(StdDraw.nextKeyTyped());
            }
        }
        return result.toString();
    }
}
