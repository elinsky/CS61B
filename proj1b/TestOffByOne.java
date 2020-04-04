import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        boolean actual = offByOne.equalChars('a', 'b');
        assertTrue(actual);
        actual = offByOne.equalChars('r', 'q');
        assertTrue(actual);
        actual = offByOne.equalChars('a', 'e');
        assertFalse(actual);
        actual = offByOne.equalChars('z', 'a');
        assertFalse(actual);
        actual = offByOne.equalChars('a', 'a');
        assertFalse(actual);
    }
}
