package rutil.shape;

public class Circle extends Shape {

    int radius;

    public Circle(int x, int y, int radius) {

        width = radius * 2;
        height = radius * 2;
        offsetX = x - radius;
        offsetY = y - radius;

        this.x = x;
        this.y = y;
        this.radius = radius;

    }

    public boolean intersects(Circle c) {

        if(offsetX > c.getX2() || offsetY > c.getY2() || getX2() < c.getOffsetX() || getY2() < c.getOffsetY()) {
            return false;
        }

        return noSqrtCheckBound(x, y, c.getX(), c.getY(), getRadius() + c.getRadius());

    }

    public void setSize(int width, int height) {

        super.setSize(width, height);
        radius = width / 2;

    }

    public int getRadius() {

        return radius;

    }

    public static boolean noSqrtCheckBound(double x1, double y1, double x2, double y2, double distance) {

        double xDistance = x1 - x2;
        double yDistance = y1 - y2;

        return xDistance * xDistance + yDistance * yDistance <= distance * distance;

    }

}
