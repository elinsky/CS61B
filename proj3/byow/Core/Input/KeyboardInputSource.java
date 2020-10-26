package byow.Core.Input;

import byow.InputDemo.InputSource;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A KeyboardInputSource listens to keystrokes, captures them, and returns them to the consumer.
 */
public class KeyboardInputSource implements InputSource {

    /**
     * Get the next key that the user pressed.
     */
    public char getNextKey() {
        return Character.toUpperCase(StdDraw.nextKeyTyped());
    }

    /**
     * Has the user pressed a key that we have yet to process?
     */
    public boolean possibleNextInput() {
        return StdDraw.hasNextKeyTyped();
    }
}
