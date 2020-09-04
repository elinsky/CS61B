import org.junit.Test;
import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern = "ell";
        assertEquals(1, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }

    @Test
    public void trickier() {
        String input = "returns a new string that is a substring of this string.  The substring begins at the...";
        String pattern = "is a";
        assertEquals(26, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }
}
