package rwt.device.render;

import rutil.text.Data;
import rwt.device.graphic.Color4f;
import rwt.device.graphic.TextFont;
import rwt.device.texture.Texture;

import java.awt.*;

public class RawDraw {

    static Graphics2D g;
    static Graphics win;

    public static void bindWindow() {

        g = (Graphics2D) DisplayDevice.buffer.getGraphics();
        win = DisplayDevice.awtPanel.getGraphics();

        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);

        g.setRenderingHints(hints);

    }

    public static void renderTexture(int x, int y, int w, int h, Texture texture) {

        g.drawImage(texture.data, x, y, w, h, null);

    }

    public static void renderRect(int x, int y, int w, int h) {

        g.fillRect(x, y, w, h);

    }

    public static void renderFrame(int x, int y, int w, int h) {

        g.drawRect(x, y, w, h);

    }

    public static void renderCircle(int x, int y, int w, int h) {

        g.fillOval(x, y, w, h);

    }

    public static void renderRing(int x, int y, int w, int h) {

        g.drawOval(x, y, w, h);

    }

    public static void color(Color4f c) {

        g.setColor(c.data);

    }

    public static void font(TextFont font) {

        g.setFont(font.data);

    }

    public static int fontSize() {

        return g.getFont().getSize();

    }

    public static void renderString(int x, int y, String str) {

        g.drawString(str, x, y + fontSize() / 2);

    }

    public static void bufferFlush(int x, int y, int w, int h) {

        win.drawImage(DisplayDevice.buffer, x, y, w, h, null);

    }

    public static int getFontLength(String str) {

        int length = 0;
        FontMetrics metrics = g.getFontMetrics();

        for(int i = 0; i < Data.lengthOf(str); i++) {
            char ch = str.charAt(i);
            length += metrics.charWidth(ch);
        }

        return length;

    }

}
