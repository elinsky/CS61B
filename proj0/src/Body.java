public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double dx = b.xxPos - this.xxPos;
        double dy = b.yyPos - this.yyPos;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public double calcForceExertedBy(Body b) {
        double G = 6.67e-11;
        double r = calcDistance(b);
        return G * this.mass * b.mass / Math.pow(r, 2);
    }

    public double calcForceExertedByX(Body b) {
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        double dx = b.xxPos - this.xxPos;
        return F * dx / r;
    }

    public double calcForceExertedByY(Body b) {
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        double dy = b.yyPos - this.yyPos;
        return F * dy / r;
    }

    public double calcNetForceExertedByX(Body[] bs) {
        double netForce = 0;
        for (Body b : bs) {
            if (!this.equals(b)) {
                netForce += calcForceExertedByX(b);
            }
        }
        return netForce;
    }


    public double calcNetForceExertedByY(Body[] bs) {
        double netForce = 0;
        for (Body b : bs) {
            if (!this.equals(b)) {
                netForce += calcForceExertedByY(b);
            }
        }
        return netForce;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        this.xxVel = this.xxVel + dt * aX;
        this.yyVel = this.yyVel + dt * aY;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "/images/" + imgFileName);
    }
}
