import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        boolean result = palindrome.isPalindrome("racecar");
        assertTrue(result);
        result = palindrome.isPalindrome("anna");
        assertTrue(result);
        result = palindrome.isPalindrome("doghouse");
        assertFalse(result);
    }

    @Test
    public void testIsPalindromeCharacterComparator() {
        boolean result = palindrome.isPalindrome("flake", new OffByOne());
        assertTrue(result);
        result = palindrome.isPalindrome("aeklfb", new OffByOne());
        assertTrue(result);
        result = palindrome.isPalindrome("anna", new OffByOne());
        assertFalse(result);
    }
}
