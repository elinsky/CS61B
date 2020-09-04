import static org.junit.Assert.assertTrue;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString {
    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;
    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;
    private final int length;
    private final StringBuilder sb;
    private int cached_hash;
    private int RM;

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert (s.length() == length);
        this.length = length;
        this.sb = new StringBuilder(s);
        this.cached_hash = hash();
        RM = 1;
        for (int i = 1; i <= length - 1; i++) {
            RM = (UNIQUECHARS * RM) % PRIMEBASE;
        }
    }

    /**
     * Adds a character to the back of the stored "string" and
     * removes the first character of the "string".
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        updateCachedHash(c);
        sb.deleteCharAt(0).append(c);
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
//        StringBuilder strb = new StringBuilder();
        /* FIX ME */
        return sb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        return length;
    }

    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        assertTrue(o instanceof RollingString);
        return this.toString().equals(o.toString());
    }

    /**
     * Calculates the hashcode of the stored "string".
     * Only used in initialization.
     */
    private int hash() {
        int h = 0;
        for (int j = 0; j < length; j++) {
            h = (UNIQUECHARS * h + sb.charAt(j)) % PRIMEBASE;
        }
        return h;
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        return cached_hash;
    }

    private void updateCachedHash(char c) {
        cached_hash = (cached_hash + PRIMEBASE - RM * sb.charAt(0) % PRIMEBASE) % PRIMEBASE;
        cached_hash = (cached_hash * UNIQUECHARS + c) % PRIMEBASE;
    }
}
