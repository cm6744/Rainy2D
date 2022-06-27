package rutil.bsck;

public class Platform {

    public static long getTickNano() {

        return System.nanoTime();

    }

    public static long getTickNs() {

        return System.currentTimeMillis();

    }

    public static String getPlatformLocal() {

        return System.getProperty("os.name");

    }

    public static void exit() {

        System.exit(0);

    }

}
