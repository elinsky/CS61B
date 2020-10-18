package byow.Core;

import java.io.Serializable;

/**
 * A point is an X Y coordinate.
 */
public class Point implements Serializable {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Point that = (Point) obj;
        return this.x == that.x & this.y == that.y;
    }
}
