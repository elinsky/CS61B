package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;

    public KDTree(List<Point> points) {

        for (Point point : points) {
            root = insert(root, point, true);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        // This should take O(logN) on average.
        Point goal = new Point(x, y);
        Node best = nearestHelper(root, goal, root, true);
        return best.p;
    }

    private Node nearestHelper(Node n, Point goal, Node best, boolean leftRight) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) { // if current node is better than the best
            best = n;
        }
        Node goodSide, badSide;
        if (leftRight) {
            if (goal.getX() < n.p.getX()) { // if the goal is to the left
                goodSide = n.leftDown;
                badSide = n.rightUp;
            } else {
                goodSide = n.rightUp;
                badSide = n.leftDown;
            }
        } else {
            if (goal.getY() < n.p.getY()) {
                goodSide = n.leftDown;
                badSide = n.rightUp;
            } else {
                goodSide = n.rightUp;
                badSide = n.leftDown;
            }
        }
        best = nearestHelper(goodSide, goal, best, !leftRight);
        if (badSide != null && worthExploration(n.p, goal, best.p, leftRight)) {
            best = nearestHelper(badSide, goal, best, !leftRight);
        }
        return best;
    }

    private boolean worthExploration(Point unexplored, Point goal, Point best, boolean leftRight) {
        double unexploredDistance;
        double bestDistance = Math.sqrt(Point.distance(goal, best));
        if (leftRight) {
            unexploredDistance = Math.abs(unexplored.getX() - goal.getX());
        } else {
            unexploredDistance = Math.abs(unexplored.getY() - goal.getY());
        }
        return unexploredDistance <= bestDistance;
    }

    private Node insert(Node tree, Point toInsert, boolean leftRight) {
        if (tree == null) {
            return new Node(toInsert, null, null);
        } else if (leftRight) {
            if (toInsert.getX() < tree.p.getX()) {
                tree.leftDown = insert(tree.leftDown, toInsert, !leftRight);
                return tree;
            } else { // Ties go to the right
                tree.rightUp = insert(tree.rightUp, toInsert, !leftRight);
                return tree;
            }
        } else {
            if (toInsert.getY() < tree.p.getY()) {
                tree.leftDown = insert(tree.leftDown, toInsert, !leftRight);
            } else { // Ties go up
                tree.rightUp = insert(tree.rightUp, toInsert, !leftRight);
            }
            return tree;
        }
    }

    private static class Node {
        Point p;
        Node leftDown;
        Node rightUp;

        public Node(Point p, Node leftDown, Node rightUp) {
            this.p = p;
            this.leftDown = leftDown;
            this.rightUp = rightUp;
        }
    }
}
