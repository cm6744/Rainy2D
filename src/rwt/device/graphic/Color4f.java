package rwt.device.graphic;

import rutil.maths.Maths;

import java.awt.*;

public class Color4f {

    public static Color4f LIGHT = new Color4f(1, 1, 1, 0.5);
    public static Color4f SHADOW = new Color4f(0, 0, 0, 0.5);
    public static Color4f WHITE = new Color4f(1, 1, 1);
    public static Color4f BLACK = new Color4f(0, 0, 0);
    public static Color4f GOLD = new Color4f(0.5, 0.2, 0.5);
    public static Color4f PURPLE = new Color4f(0.5, 0.2, 0.5);

    ///////////*********//////////

    public Color data;

    double red;
    double green;
    double blue;
    double alpha;

    //rgba:0~1.0
    public Color4f(double r, double g, double b, double a) {

        r = checkIn(r);
        g = checkIn(g);
        b = checkIn(b);
        a = checkIn(a);

        data = new Color(
                Maths.toInt(r * 255),
                Maths.toInt(g * 255),
                Maths.toInt(b * 255),
                Maths.toInt(a * 255)
        );

        red = r;
        green = g;
        blue = b;
        alpha = a;

    }

    public static double checkIn(double i) {

        if(i < 0) {
            return 0;
        }
        if(i > 1) {
            return 1;
        }

        return i;

    }

    public Color4f(double r, double g, double b) {

        this(r, g, b, 1);

    }

    public Color4f retrans(double a) {

        return new Color4f(red, green, blue, a);

    }

}
