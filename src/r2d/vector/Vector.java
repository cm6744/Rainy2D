package r2d.vector;

import r2d.element.Element;
import r2d.element.vector.ElementVector;
import rutil.maths.Maths;
import rutil.shape.Rectangle;

public class Vector {

    public static double opposite(double angle) {

        return 180 + angle;

    }

    public static double vectorX(double x, double speed, double angle) {

        return x + speed * Math.cos(Maths.toRadians(angle));

    }

    public static double vectorY(double y, double speed, double angle) {

        return y + speed * Math.sin(Maths.toRadians(angle));

    }

    //1:发起者 2:接受者
    public static double angleBetweenAB(double x1, double y1, double x2, double y2) {

        return Maths.toAngle(Math.atan2(y1 - y2, x1 - x2));

    }

    public static double angleBetweenAB(Element e1, Element e2) {

        return angleBetweenAB(e1.getX(), e1.getY(), e2.getX(), e2.getY());

    }

    public static double distanceBetweenAB(double x1, double y1, double x2, double y2) {

        int xDistance = Maths.toInt(x1 - x2);
        int yDistance = Maths.toInt(y1 - y2);

        return Maths.sqrt(xDistance * xDistance + yDistance * yDistance);

    }

    public static double distanceBetweenAB(Element e1, Element e2) {

        return distanceBetweenAB(e1.getX(), e1.getY(), e2.getX(), e2.getY());

    }

    public static boolean isOnLine(double x, double y, double x1, double y1, double x2, double y2) {

        return distanceBetweenAB(x, y, x1, y1) + distanceBetweenAB(x, y, x2, y2) <= distanceBetweenAB(x2, y2, x1, y1);

    }

    public static boolean checkRebound(ElementVector e, Rectangle rect, boolean checkBottom) {

        return rebound(e, rect, checkBottom) != e.getAngle();

    }

    public static double rebound(ElementVector e, Rectangle rect, boolean checkBottom) {

        if(e.getX() < rect.getOffsetX() || e.getX() > rect.getX2()) {
            return -e.getAngle() - 180;
        }
        else if(e.getY() < rect.getOffsetY() || (e.getY() > rect.getY2() && checkBottom)) {
            return -e.getAngle();
        }

        return e.getAngle();

    }

}
