package bearmaps.proj2ab;

import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;

import java.util.List;

public class NaivePointSet implements PointSet {
    private final List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point source = new Point(x, y);
        Point nearestNeighbor = points.get(0);
        double shortestDistance = Point.distance(source, nearestNeighbor);
        for (Point candidateNeighbor : points) {
            double candidateDistance = Point.distance(source, candidateNeighbor);
            if (candidateDistance < shortestDistance) {
                nearestNeighbor = candidateNeighbor;
                shortestDistance = candidateDistance;
            }
        }
        return nearestNeighbor;
    }
}
