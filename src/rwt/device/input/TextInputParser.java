package rwt.device.input;

import rutil.maths.ToolMath;
import rutil.text.Data;
import rutil.text.VariableString;

import java.awt.*;

import static java.awt.event.KeyEvent.*;

public class TextInputParser {

    public static int downTimeBuffer = 25;

    VariableString putter = new VariableString();
    int cursor;

    boolean canParse = false;
    boolean shift;
    boolean capslock;

    public void parse() {

        if(canParse) {
            capslock = Toolkit.getDefaultToolkit().getLockingKeyState(Keys.CAPSLOCK);
            shift = Input.isKeyDown(Keys.SHIFT);
            boolean backspace = Input.isKeyPress(Keys.BACKSPACE) || Input.getKeyDownTime(Keys.BACKSPACE) > downTimeBuffer;
            boolean parseIn = Input.isKeyPress(Input.codeOfKey()) || Input.getKeyDownTime(Input.codeOfKey()) > downTimeBuffer;

            String input = getKeyText(Input.codeOfKey(), shift);

            if(parseIn && Data.lengthOf(input) == 1 && !backspace) {
                putter.add(capslock ? input.toUpperCase() : input.toLowerCase());
                tranCursor(1);
            }
            if(backspace) {
                putter.remove(1);
                tranCursor(-1);
            }
        }

    }

    public int getCursor() {

        return cursor;

    }

    public void tranCursor(int t) {

        if(t > 0) {
            cursor = ToolMath.ofInt(cursor, t, putter.length());
        }
        else {
            cursor = ToolMath.ofInt(cursor, t);
        }

    }

    public void setCanParse(boolean can) {

        canParse = can;

    }

    public boolean canParse() {

        return canParse;

    }

    public void setText(String str) {

        clear();
        putter.add(str);

    }

    public String getText() {

        return putter.create();

    }

    public void clear() {

        putter.clear();

    }

    private static String getKeyText(int keyCode, boolean shift) {

        if(keyCode >= VK_A && keyCode <= VK_Z) {
            return String.valueOf((char) keyCode);
        }

        switch(keyCode) {
            case VK_SPACE:
                return " ";
            case VK_PERIOD:
                return ".";
            case VK_9:
                return shift ? "(" : "9";
            case VK_0:
                return shift ? ")" : "0";
            case VK_2:
                return shift ? "@" : "2";
            case VK_1:
                return shift ? "!" : "1";
            case VK_3:
                return shift ? "#" : "3";
            case VK_4:
                return shift ? "$" : "4";
            case VK_5:
                return shift ? "%" : "5";
            case VK_6:
                return shift ? "^" : "6";
            case VK_7:
                return shift ? "&" : "7";
            case VK_8:
                return shift ? "*" : "8";
            case VK_COMMA:
                return shift ? "<" : ",";
            case VK_CLOSE_BRACKET:
                return shift ? "}" : "]";
            case VK_OPEN_BRACKET:
                return shift ? "{" : "[";
            case VK_EQUALS:
                return shift ? "+" : "=";
            case VK_SEMICOLON:
                return shift ? ":" : ";";
            case VK_MINUS:
                return shift ? "_" : "-";
            case VK_SLASH:
                return shift ? "?" : "/";
        }

        return Data.BLANK;

    }

    public boolean isShiftOn() {

        return shift;

    }

    public boolean isCapslockOn() {

        return capslock;

    }

}
