public class NBody {
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String fileName) {
        In in = new In(fileName);
        in.readInt();
        in.readDouble();
        Body[] bodies = new Body[5];
        for (int i = 0; i < 5; i++) {
            double xCoord = in.readDouble();
            double yCoord = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            Body b = new Body(xCoord, yCoord, xVel, yVel, mass, img);
            bodies[i] = b;
        }
        return bodies;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "/images/starfield.jpg");
        for (Body body : bodies) {
            body.draw();
        }
        StdDraw.show();

        //* Start the animation. */
        double time = 0;
        double[] xForces = new double[bodies.length];
        double[] yForces = new double[bodies.length];

        //* Refresh the image at each time increment. */
        while (time < T) {

            //* Calculate net forces on all the bodies. */
            for (int i = 0; i < bodies.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            //* Update the position of each body. */
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].update(dt, xForces[i], yForces[i]);
            }

            //* Draw the background image. */
            StdDraw.picture(0, 0, "/images/starfield.jpg");

            //* Draw all the bodies. */
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}
