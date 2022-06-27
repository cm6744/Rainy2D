package rutil.maths;

public class ToolMath {

    public static double ofDouble(double value, double crease, double limit) {

        if(crease > 0) {
            if(value + crease < limit) {
                return value + crease;
            }
            else if(value + crease >= limit) {
                return limit;
            }
        }
        else if(crease < 0) {
            if(value + crease > limit) {
                return value + crease;
            }
            else if(value + crease <= limit) {
                return limit;
            }
        }

        return value;

    }

    public static double ofDouble(double value, double crease) {

        return ofDouble(value, crease, 0);

    }

    public static int ofInt(int value, int crease, int limit) {

        return Maths.toInt(ofDouble(value, crease, limit));

    }

    public static int ofInt(int value, int crease) {

        return ofInt(value, crease, 0);

    }

}
