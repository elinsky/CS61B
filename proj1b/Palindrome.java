public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        int size = wordDeque.size();
        for (int i = 0; i < size / 2; i++) {
            if (wordDeque.removeFirst() != wordDeque.removeLast()) {
                return false;
            }
        }
        return true;
    }

    /** This method will return true if the word is a palindrome according to
     * the charater comparison test provided by the CharacterComparator passed
     * in as argument cc. */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        /** TODO implement the method. */
        char first, last;
        Deque<Character> wordDeque = wordToDeque(word);
        int size = wordDeque.size();
        for (int i = 0; i < size / 2; i++) {
            first = wordDeque.removeFirst();
            last = wordDeque.removeLast();
            if (!cc.equalChars(first, last)) {
                return false;
            }
        }
        return true;
    }
}
