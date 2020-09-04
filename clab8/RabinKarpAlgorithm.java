public class RabinKarpAlgorithm {

    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        // store the hash of the pattern I'm looking for
        RollingString rs_pattern = new RollingString(pattern, pattern.length());
        int pattern_hash = rs_pattern.hashCode();
        int n = input.length();
        int m = rs_pattern.length();

        RollingString rs_input = new RollingString(input.substring(0, m) , m);
        if (rs_input.hashCode() == pattern_hash) {
            return 0;
        }
        for (int i = m; i < n; i++) {
            rs_input.addChar(input.charAt(i));
            if (rs_input.hashCode() == pattern_hash) {
                return i - m + 1;
            }
        }
        return -1;


    }

}
