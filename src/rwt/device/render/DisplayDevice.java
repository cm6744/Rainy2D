package rwt.device.render;

import rutil.bsck.Logs;
import rutil.bsck.Platform;
import rwt.device.input.Input;
import rwt.device.sound.SoundDevice;
import rwt.device.texture.Texture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.MemoryImageSource;
import java.awt.image.VolatileImage;

public class DisplayDevice {

    static boolean created;

    public static JFrame awtFrame;
    public static JPanel awtPanel;
    public static VolatileImage buffer;

    public static int width;
    public static int height;
    public static int xSize;
    public static int ySize;

    public static int DEFAULT = 0;
    public static int FULL = 1;

    public static DisplayMode defaultMode;
    public static GraphicsEnvironment environment;
    public static GraphicsDevice device;
    public static Toolkit toolkit;

    private static void initEnvironment() {

        environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
        toolkit = Toolkit.getDefaultToolkit();

    }

    public static void openWindow(String title, Texture icon, int w, int h, int mode, int rf, int bd) {

        initEnvironment();

        xSize = toolkit.getScreenSize().width;
        ySize = toolkit.getScreenSize().height;

        width = w;
        height = h;

        awtPanel = new JPanel();
        awtFrame = new JFrame();

        awtPanel.setPreferredSize(new Dimension(width, height));
        awtPanel.setSize(width, height);

        awtFrame.setSize(width, height);
        awtFrame.add(awtPanel);

        awtFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Platform.exit();//write logs before exit
            }
        });
        awtFrame.setTitle(title);
        awtFrame.setResizable(false);

        if(mode == FULL) {
            awtFrame.setUndecorated(true);//full window mode
            customDeviceFullFrame(rf, bd);
        }
        else if(mode == DEFAULT) {
            awtFrame.pack();//windowed mode
            awtFrame.setLocation((xSize - awtFrame.getWidth()) / 2, (ySize - awtFrame.getHeight()) / 2);
        }
        else {
            Logs.log("No such window mode! Please check arg [int mode].");
            Platform.exit();
        }

        if(icon != null) {
            setIcon(icon);
        }

        created = true;
        buffer = awtPanel.createVolatileImage(w, h);

        Input.createCollector();//open input
        SoundDevice.createSoundDevice();//open sound thread
        RawDraw.bindWindow();//update draw device

    }

    public static void setIcon(Texture texture) {

        awtFrame.setIconImage(texture.source());

    }

    public static void customDeviceFullFrame(int refresh, int bitDepth) {

        DisplayMode[] dms = device.getDisplayModes();

        device.setFullScreenWindow(awtFrame);

        DisplayMode d = new DisplayMode(width, height, bitDepth, refresh);

        for(int i = 0; i < dms.length; i++) {
            DisplayMode dm = dms[i];
            if(dm.equals(d)) {
                device.setDisplayMode(dm);
                defaultMode = dm;
                return;
            }
        }

        Logs.err("No such display mode. Please check the [refresh][bit_depth] in the properties.");
        Logs.err("If it still doesn't work, change the width and height of window.");
        Logs.err("Also, you can check which size your computer supports.");
        Platform.exit();

    }

    public static void printAllModes() {

        DisplayMode[] dms = device.getDisplayModes();

        for(int i = 0; i < dms.length; i++) {
            DisplayMode dm = dms[i];
            Logs.log(
                    "width: " + dm.getWidth() +
                    ", height: " + dm.getHeight() +
                    ", refresh: " + dm.getRefreshRate() +
                    ", bit_depth: " + dm.getBitDepth()
                    );
        }

    }

    public static void endOption() {

        awtFrame.setVisible(true);

    }

    public static void hideMouse() {

        Image texture = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(0, 0, new int[0], 0, 0));
        awtFrame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(texture, new Point(0, 0), ""));

    }

    public static int getWindowX() {

        //panel is not located.
        //insets is the frame of jFrame.
        //this will get panel's pos.
        return awtFrame.getX() + awtFrame.getInsets().left;

    }

    public static int getWindowY() {

        return awtFrame.getY() + awtFrame.getInsets().top;

    }

    public static int getWindowWidth() {

        return width;

    }

    public static int getWindowHeight() {

        return height;

    }

}
