package byow.Core.Input;

/**
 * Created by hug.
 */
import byow.InputDemo.InputSource;
import edu.princeton.cs.algs4.StdDraw;

public class KeyboardInputSource implements InputSource {

    public char getNextKey() {
        return Character.toUpperCase(StdDraw.nextKeyTyped());
    }

    public boolean possibleNextInput() {
        return StdDraw.hasNextKeyTyped();
    }
}
