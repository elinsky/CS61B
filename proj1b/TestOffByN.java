import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByN {

    static CharacterComparator offByN = new OffByN(2);

    @Test
    public void testEqualChars() {
        boolean actual = offByN.equalChars('a', 'c');
        assertTrue(actual);
        actual = offByN.equalChars('r', 'p');
        assertTrue(actual);
        actual = offByN.equalChars('a', 'e');
        assertFalse(actual);
        actual = offByN.equalChars('z', 'a');
        assertFalse(actual);
        actual = offByN.equalChars('a', 'a');
        assertFalse(actual);
    }
}
