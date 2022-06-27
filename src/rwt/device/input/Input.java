package rwt.device.input;

import rutil.maths.Maths;
import rwt.device.render.DisplayDevice;

import java.awt.*;
import java.awt.event.*;

public class Input {

    static int codeKey;
    static boolean[] keyDown = new boolean[512];
    static int[] keyTimer = new int[512];

    static boolean[] mouseDown = new boolean[256];
    static int[] mouseTimer = new int[256];

    static double percent = 1.0;
    static int left;
    static int top;

    public static boolean keyboardCollector = true;
    public static boolean mouseCollector = true;

    public static void createCollector() {

        KeyAdapter key = new KeyAdapter() {

            public void keyPressed(KeyEvent e) {

                if(keyboardCollector) {
                    keyDown[e.getKeyCode()] = true;
                    codeKey = e.getKeyCode();
                }

            }

            public void keyReleased(KeyEvent e) {

                if(keyboardCollector) {
                    keyDown[e.getKeyCode()] = false;
                    if(codeKey == e.getKeyCode()) {
                        codeKey = 0;
                    }
                }

            }

        };
        MouseAdapter mouse = new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if(mouseCollector) {
                    mouseDown[e.getButton()] = true;
                }

            }

            public void mouseReleased(MouseEvent e) {

                if(mouseCollector) {
                    mouseDown[e.getButton()] = false;
                }

            }

        };

        DisplayDevice.awtFrame.addKeyListener(key);
        DisplayDevice.awtFrame.addMouseListener(mouse);

    }

    public static void tick() {

        for(int i = 0; i < keyTimer.length; i++) {
            updateKeyState(i);
        }

        for(int i = 0; i < mouseTimer.length; i++) {
            updateMouseState(i);
        }

    }

    private static void updateKeyState(int keyCode) {

        if(isKeyDown(keyCode)) {
            keyTimer[keyCode]++;//计算按键时间
        }
        else {
            keyTimer[keyCode] = 0;
        }

    }

    private static void updateMouseState(int mouseCode) {

        if(isMouseDown(mouseCode)) {
            mouseTimer[mouseCode]++;
        }
        else {
            mouseTimer[mouseCode] = 0;
        }

    }

    public static int getKeyDownTime(int keyCode) {

        return keyTimer[keyCode];

    }

    public static int getMouseDownTime(int mouseCode) {

        return mouseTimer[mouseCode];

    }

    public static boolean isKeyDown(int keyCode) {

        return keyDown[keyCode];

    }

    public static boolean isMouseDown(int mouseCode) {

        return mouseDown[mouseCode];

    }

    public static boolean isKeyPress(int keyCode) {

        return getKeyDownTime(keyCode) == 1;

    }

    public static boolean isMouseClicking(int mouseCode) {

        return getMouseDownTime(mouseCode) == 1;

    }

    public static int getMouseX() {

        return getFullMouseX() - left - DisplayDevice.getWindowX();

    }

    public static int getMouseY() {

        return getFullMouseY() - top - DisplayDevice.getWindowY();

    }

    public static int getFullMouseX() {

        return Maths.toInt(MouseInfo.getPointerInfo().getLocation().x / percent);

    }

    public static int getFullMouseY() {

        return Maths.toInt(MouseInfo.getPointerInfo().getLocation().y / percent);

    }

    /**
     * @param pr is the overPercent of the canvas
     * @param cl is C_LEFT (not include window's posX)
     * @param ct is C_TOP (not include window's posY)
     */
    public static void option(double pr, int cl, int ct) {

        percent = pr;
        left = cl;
        top = ct;

    }

    public static int codeOfKey() {

        return codeKey;

    }

}
