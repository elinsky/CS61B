public class TestBody {

    /** Tests pairwise force. */
    public static void main(String[] args) { checkPairwiseForce(); }

    public static void checkEquals(double actual, double expected, String label, double eps) {
        if (Double.isNaN(actual) || Double.isInfinite(actual)) {
            System.out.println("FAIL: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        } else if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        }
    }

    public static void checkPairwiseForce() {
        System.out.println("Checking pairwise force...");
        Body b1 = new Body(0, 0, -5, 0, 5, "jupiter.gif");
        Body b2 = new Body(5, 0, 5, 0, 5, "jupiter.gif");
        checkEquals(b1.calcForceExertedByX(b2), -1 * b2.calcForceExertedByX(b1), "calcForceExertedByX", 0.01);
    }
}
