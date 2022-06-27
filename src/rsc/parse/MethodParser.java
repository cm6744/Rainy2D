package rsc.parse;

import rutil.container.Array;
import rutil.container.Map;
import rutil.maths.Maths;
import rutil.text.Data;
import rutil.text.Parser;

public class MethodParser {

    private static Map<String[]> cacheArgs = new Map<>();

    public static String BRACKET_L = "{";
    public static String BRACKET_R = "}";
    public static String S_ARGS = ",";
    public static String S_END = ";";
    public static String S_DESC = "#";

    public static boolean checkDesc(String prop) {

        String cut = Data.compress(prop);
        return Data.startWith(cut, S_DESC) || Data.isEmpty(cut);

    }

    /**
     * compress all commands and cut off space.
     * if a command like:
     * ---
     * set_image = {
     *     path = "textures/image_01",
     *     offset = 12
     * };
     * ---
     * it will be compressed as:
     * ---
     * set_image={path="textures/image_01",offset=12};
     */
    public static Array<String> compress(Array<String> commands) {

        Array<String> result = new Array<>();
        String temp;
        String last = Data.BLANK;

        for(int i = 0; i < commands.size(); i++) {
            temp = commands.get(i);
            temp = Data.compress(temp);

            if(MethodParser.checkDesc(temp)) {
                continue;
            }

            if(result.size() == 0) {
                result.add(temp);//first time, add a start str
            }
            else {
                if(Data.contain(last, S_END)) {
                    result.add(temp);//if last is end, add a new line
                }
                else {
                    //if last command hasn't ended, add temp into it
                    result.set(result.last(), result.getLast() + temp);
                }
            }

            last = temp;//in the next loop it is the last.
        }

        return result;

    }

    private static String[] getArgs(String command) {

        String[] argsC = cacheArgs.get(command);

        if(argsC != null) {
            return argsC;
        }

        String arg = Data.subString(command, BRACKET_L, BRACKET_R);//get arg line in {...};
        arg = Data.compress(arg);//remove spaces
        String[] args = Data.cutBy(arg, S_ARGS);//cut by comma

        cacheArgs.set(command, args);//put into cache list

        return args;

    }

    private static String getArg(String command, String key) {

        String[] args = getArgs(command);
        String temp;
        String keyTemp;

        for(int i = 0; i < args.length; i++) {
            temp = args[i];//arg
            keyTemp = ValueParser.parseKey(temp);//key

            if(Data.compare(key, keyTemp)) {//if key equals
                return ValueParser.parseStr(temp);//use value parser
            }
        }

        return Data.BLANK;

    }

    public static boolean hasKey(String command, String key) {

        return Data.contain(command, key);

    }

    public static String getString(String command, String key) {

        return getArg(command, key);

    }

    public static double getDouble(String command, String key) {

        return Parser.parseDouble(getString(command, key));

    }

    public static int getInt(String command, String key) {

        return Maths.toInt(getDouble(command, key));
        //must use Math.toInt, because the result of getTyped is a double String(like 1.0), cannot be parsed into Int.

    }

    public static boolean getBoolean(String command, String key) {

        return Parser.parseBoolean(getString(command, key));

    }

    public static boolean isCommand(String command, String name) {

        return Data.startWith(command, name);

    }

}
