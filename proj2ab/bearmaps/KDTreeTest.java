package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KDTreeTest {

    @Test
    public void testRandomPoints1() {
        // Arrange
        Random rand = new Random(74);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(rand.nextDouble(), rand.nextDouble());
            points.add(p);
        }
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        // Act
        Point naiveBest = naive.nearest(x, y);
        Point kdBest = kd.nearest(x, y);

        // Assert
        assertEquals(naive.nearest(x, y), kd.nearest(x, y));
    }

    @Test
    public void testRandomPoints2() {
        // Arrange
        Random rand = new Random(75);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(rand.nextDouble(), rand.nextDouble());
            points.add(p);
        }
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        // Act
        Point naiveBest = naive.nearest(x, y);
        Point kdBest = kd.nearest(x, y);

        // Assert
        assertEquals(naive.nearest(x, y), kd.nearest(x, y));
    }

    @Test
    public void testRandomPoints3() {
        // Arrange
        Random rand = new Random(76);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(rand.nextDouble(), rand.nextDouble());
            points.add(p);
        }
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        // Act
        Point naiveBest = naive.nearest(x, y);
        Point kdBest = kd.nearest(x, y);

        // Assert
        assertEquals(naive.nearest(x, y), kd.nearest(x, y));
    }

    @Test
    public void testRandomPoints4() {
        // Arrange
        Random rand = new Random(77);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(rand.nextDouble(), rand.nextDouble());
            points.add(p);
        }
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        // Act
        Point naiveBest = naive.nearest(x, y);
        Point kdBest = kd.nearest(x, y);

        // Assert
        assertEquals(naive.nearest(x, y), kd.nearest(x, y));
    }

    @Test
    public void testRandomPoints5() {
        // Arrange
        Random rand = new Random(78);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(rand.nextDouble(), rand.nextDouble());
            points.add(p);
        }
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        // Act
        Point naiveBest = naive.nearest(x, y);
        Point kdBest = kd.nearest(x, y);

        // Assert
        assertEquals(naive.nearest(x, y), kd.nearest(x, y));
    }

    @Test
    public void testRandomPoints6() {
        // Arrange
        Random rand = new Random(79);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(rand.nextDouble(), rand.nextDouble());
            points.add(p);
        }
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        // Act
        Point naiveBest = naive.nearest(x, y);
        Point kdBest = kd.nearest(x, y);

        // Assert
        assertEquals(naive.nearest(x, y), kd.nearest(x, y));
    }

    @Test
    public void testRandomPoints7() {
        // Arrange
        Random rand = new Random(80);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(rand.nextDouble(), rand.nextDouble());
            points.add(p);
        }
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        // Act
        Point naiveBest = naive.nearest(x, y);
        Point kdBest = kd.nearest(x, y);

        // Assert
        assertEquals(naive.nearest(x, y), kd.nearest(x, y));
    }

    @Test
    public void testRandomPoints8() {
        // Arrange
        Random rand = new Random(81);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(rand.nextDouble(), rand.nextDouble());
            points.add(p);
        }
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        // Act
        Point naiveBest = naive.nearest(x, y);
        Point kdBest = kd.nearest(x, y);

        // Assert
        assertEquals(naive.nearest(x, y), kd.nearest(x, y));
    }
}
