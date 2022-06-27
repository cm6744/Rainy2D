package r2d.vector;

public class Gravity {

    public static final double G = 1 / 9.8;
    public static final double MAX_FALL_SPEED = 4;

    public static double gravityFall(double y, int overLandTime, double weight) {

        double cache = y + overLandTime * G * weight;
        double max = y + MAX_FALL_SPEED * weight;

        return cache < max ? cache : max;

    }

    public static double gravityJump(double y, int overLandTime, double jumpForce, double weight) {

        double cache = y + (overLandTime * G * weight - jumpForce);
        double max = y + MAX_FALL_SPEED * weight;

        if(overLandTime > 0) {
            return cache < max ? cache : max;
        }

        return y;

    }

}
