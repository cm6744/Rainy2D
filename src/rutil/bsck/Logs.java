package rutil.bsck;

import rutil.container.Array;
import rutil.text.Data;

public class Logs {

    public static String REPLACEMENT = "{}";
    public static Array<String> logs = new Array<>();

    public static void log(String info) {

        logs.add("[Log] " + info);

    }

    public static void err(String info) {

        logs.add("[Error] " + info);

    }

    public static void log(String info, String rep) {

        log(Data.replace(info, REPLACEMENT, rep));

    }

    public static void err(String info, String rep) {

        err(Data.replace(info, REPLACEMENT, rep));

    }

}
