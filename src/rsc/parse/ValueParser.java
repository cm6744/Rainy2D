package rsc.parse;

import rutil.maths.Maths;
import rutil.text.Data;
import rutil.text.Parser;

public class ValueParser {

    public static String S_VALUE = "=";
    public static String K_SP = "<$s>";//space
    public static String K_LB = "<$lb>";//{
    public static String K_RB = "<$rb>";//}
    public static String K_CM = "<$cm>";//,
    public static String K_EQ = "<$eq>";//=

    public static String parseKey(String prop) {

        return Data.lastString(Data.compress(prop), S_VALUE);//get the key of the expression

    }

    public static String parseStr(String prop) {
        
        String cut = Data.compress(prop);
        String rep;
        rep = Data.replace(cut, K_SP, Data.SPACE);
        rep = Data.replace(rep, K_LB, "{");
        rep = Data.replace(rep, K_RB, "}");
        rep = Data.replace(rep, K_CM, ",");
        rep = Data.replace(rep, K_EQ, "=");

        return Data.nextString(rep, S_VALUE);//if not, return without space

    }

    public static boolean parseBoolean(String prop) {

        return Parser.parseBoolean(parseStr(prop));

    }

    public static double parseDouble(String prop) {

        return Parser.parseDouble(parseStr(prop));

    }

    public static int parseInt(String prop) {

        return Maths.toInt(parseDouble(prop));

    }

}
