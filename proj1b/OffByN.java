public class OffByN implements CharacterComparator {
    private int N;

    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int xInt = Character.getNumericValue(x);
        int yInt = Character.getNumericValue(y);
        return xInt == yInt + N || xInt == yInt - N;
    }
}
