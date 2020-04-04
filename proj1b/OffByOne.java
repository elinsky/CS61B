public class OffByOne implements CharacterComparator {
    /** Return true for characters that are different by exactly one. */
    @Override
    public boolean equalChars(char x, char y) {
        int xInt = Character.getNumericValue(x);
        int yInt = Character.getNumericValue(y);
        return xInt == yInt + 1 || xInt == yInt - 1;
    }
}
