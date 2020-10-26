package byow.Core.Input;

import byow.InputDemo.InputSource;

/**
 * A StringInputDevice accepts a string as input, and allows the consumer to retrieve each character, one at a time,
 * as if it was a keypress.
 */
public class StringInputDevice implements InputSource {
    private String input;
    private int index;

    /**
     * Creates an input device from a string.
     */
    public StringInputDevice(String s) {
        index = 0;
        input = s;
    }

    /**
     * Pulls a character off the front of the string, and returns it as if it was a keypress.
     */
    public char getNextKey() {
        char returnChar = input.charAt(index);
        index += 1;
        return returnChar;
    }

    /**
     * Are there still characters in the string to process?
     */
    public boolean possibleNextInput() {
        return index < input.length();
    }
}
