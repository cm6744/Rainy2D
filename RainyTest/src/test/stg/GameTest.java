package test.stg;

import r2d.factory.ElementFactory;
import r2d.render.desktop.Canvas;
import r2d.render.desktop.Screen;
import r2dx.stg.factory.STGFactory;
import rsc.Properties;
import rutil.text.Data;
import rwt.device.render.DisplayDevice;
import rwt.device.texture.Texture;
import rwt.resource.Reader;
import test.stg.screen.InGameCanvas;
import test.stg.screen.MenuCanvas;

public class GameTest {

    public static Screen screen;
    public static Canvas mc;
    public static InGameCanvas igc;
    public static Texture loading = Reader.image("textures/font/loading");

    public static void main(String[] args) {

        ElementFactory.init();
        STGFactory.init();

        Properties p = new Properties("properties/func_player");
        if(p.booleanKey("print_all_display")) {
            DisplayDevice.printAllModes();
        }

        int mode = -1;
        String kem = p.stringKey("display");
        if(Data.compare(kem, "full_window")) {
            mode = DisplayDevice.FULL;
        }
        else if(Data.compare(kem, "windowed")) {
            mode = DisplayDevice.DEFAULT;
        }

        DisplayDevice.openWindow(
                p.stringKey("title"),
                Reader.image(p.stringKey("icon_path")),
                p.intKey("width"),
                p.intKey("height"),
                mode,
                p.intKey("refresh"),
                p.intKey("bit_depth")
        );
        DisplayDevice.hideMouse();
        DisplayDevice.endOption();
        screen = new Screen();
        mc = new MenuCanvas(screen);
        igc = new InGameCanvas(screen);
        screen.loadFromCanvas(mc);
        //screen.loadFromCanvas(new CanvasFunc(screen));
        screen.setDefaultFps(p.intKey("fps"));

    }

}
