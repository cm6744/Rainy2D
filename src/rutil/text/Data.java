package rutil.text;

public class Data {

    public static String BLANK = "";
    public static String SPACE = " ";

    public static char charAt(String str, int index) {

        return str.charAt(index);

    }

    public static int lengthOf(String str) {

        if(str == null) {
            return 0;
        }

        return str.length();

    }

    /**
     * check str is not "", " " or null
     */
    public static boolean isEmpty(String str) {

        if(str == null) {
            return true;
        }

        return compare(str, BLANK) || compare(str, SPACE) || lengthOf(str) == 0;

    }

    public static boolean compare(String s1, String s2) {

        if(s1 == null || s2 == null) {
            return false;
        }

        return s1.equals(s2);

    }

    /**
     * find first {find}'s index of {str}
     */
    public static int indexOf(String str, String find) {

        if(str == null) {
            return -1;
        }

        return str.indexOf(find);

    }

    /**
     * replace all {find} in {str} with {rep}
     */
    public static String replace(String str, String find, String rep) {

        return str.replace(find, rep);

    }

    //int 3 method find sub

    /**
     * example:
     * subString("0123456", 0, 5) -> return "1234"
     */
    public static String subString(String str, int a, int b) {

        if(a == -1) {
            return BLANK;
        }

        return str.substring(a + 1, b);//加一才能正确切割

    }

    /**
     * example:
     * nextString("0123456", 3) -> return "456"
     */
    public static String nextString(String str, int a) {

        if(a == -1) {
            return BLANK;
        }

        return str.substring(a + 1);//加一才能正确切割

    }

    /**
     * example:
     * lastString("0123456", 3) -> return "012"
     */
    public static String lastString(String str, int a) {

        if(a == -1) {
            return BLANK;
        }

        return str.substring(0, a);//加一才能正确切割

    }

    //str 3 method find sub

    public static String subString(String str, String spikeA, String spikeB) {

        return subString(str, indexOf(str, spikeA), indexOf(str, spikeB));

    }

    public static String nextString(String str, String spike) {

        return nextString(str, indexOf(str, spike));

    }

    public static String lastString(String str, String spike) {

        return lastString(str, indexOf(str, spike));

    }

    //checking & turning

    public static boolean startWith(String str, String comp) {

        return str.startsWith(comp);

    }

    public static String toString(int n) {

        return Integer.toString(n);

    }

    public static String toString(double n) {

        return Double.toString(n);

    }

    public static String toString(double n, int scale) {

        return String.format("%." + scale + "f" , n);

    }

    public static String toString(boolean b) {

        return String.valueOf(b);

    }

    public static boolean contain(String str, String cont) {

        return str.contains(cont);

    }

    public static String compress(String str) {

        return replace(str, SPACE, BLANK);

    }

    /**
     * example:
     * cutBy("0:1:2", ":") -> return {"0", "1", "2"}
     */
    public static String[] cutBy(String str, String cut) {

        return str.split(cut);

    }

}
