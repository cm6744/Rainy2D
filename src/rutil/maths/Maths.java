package rutil.maths;

import rutil.text.Data;

public class Maths {

    public static final int MAX = 2022022517;
    public static final int MIN = -2022022517;

    public static final double PI = 3.1415926535;
    private static final double RPI = 180 / PI;

    public static Random ran = new Random();
    public static Random sec = new Random();

    public static double random() {

        return random(0, 1);

    }

    public static double random(double min, double max) {

        return ran.genPercent() * (max - min) + min;

    }

    public static int randomInt(int min, int max) {

        return toInt(random(min, max));

    }

    public static double sinEase(double timer, double sub) {

        return (Math.sin(timer / sub - Maths.PI / 2) + 1) / 2;

    }

    public static double sinCycle(double timer, double sub) {

        return (Math.sin(timer / sub - Maths.PI / 2) + 1) / 2 - 0.5;

    }

    public static int max(int value1, int value2) {

        return value1 > value2 ? value1 : value2;

    }

    public static double max(double value1, double value2) {

        return value1 > value2 ? value1 : value2;

    }

    public static int min(int value1, int value2) {

        return value1 < value2 ? value1 : value2;

    }

    public static double min(double value1, double value2) {

        return value1 < value2 ? value1 : value2;

    }

    public static int abs(int value) {

        return value >= 0 ? value : -value;

    }

    public static double abs(double value) {

        return value >= 0 ? value : -value;

    }

    public static int toInt(double value) {

        return (int) Math.round(value);

    }

    public static double toDouble(int value) {

        return Double.valueOf(value);

    }

    public static boolean similarCompare(double value1, double value2, double maxField) {

        return abs(value1 - value2) < maxField;

    }

    public static double toRadians(double angle) {

        return angle / RPI;

    }

    public static double toAngle(double theta) {

        return theta * RPI;

    }

    public static int sizeOf(int value) {

        return Data.toString(value).length();

    }

    public static double sqrt(double value) {

        return Math.sqrt(value);

    }

}
