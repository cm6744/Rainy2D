package rwt.device.graphic;

import rutil.maths.Maths;
import rutil.shape.Circle;
import rutil.shape.Rectangle;
import rwt.device.render.RawDraw;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

public class Draw {

    public static void render(double offsetX, double offsetY, int width, int height, Texture texture) {

        RawDraw.renderTexture(Maths.toInt(offsetX), Maths.toInt(offsetY), width, height, texture);

    }

    /*----------IMPL----------*/

    /**
     * 自动切割图片绘制，需要保证图片与画布大小一致
     */
    public static void renderCut(int offsetX, int offsetY, int width, int height, Texture texture) {

        renderCut(offsetX, offsetY, width, height, offsetX, offsetY, width, height, texture);

    }

    public static void renderCut(int ix, int iy, int iw, int ih, double offsetX, double offsetY, int width, int height, Texture texture) {

        render(offsetX, offsetY, width, height, TexSystem.cut(ix, iy, iw, ih, texture));

    }

    public static void renderIn(double x, double y, int width, int height, Texture texture) {

        render(x - width / 2, y - height / 2, width, height, texture);

    }

    public static void renderIn(double x, double y, Texture texture) {

        int w = texture.getWidth();
        int h = texture.getHeight();

        render(x - w / 2, y - h / 2, w, h, texture);

    }

    public static void render(double offsetX, double offsetY, Texture texture) {

        render(offsetX, offsetY, texture.getWidth(), texture.getHeight(), texture);

    }

    /*-------SHAPE & STR RENDERING-------*/

    public static void font(TextFont font) {

        RawDraw.font(font);

    }

    public static void font(String name, int size) {

        font(new TextFont(name, size));

    }

    public static void color(Color4f c) {

        RawDraw.color(c);

    }

    public static void color4f(int r, int g, int b, double a) {

        color(new Color4f(r, g, b, a));

    }

    public static void color3f(int r, int g, int b) {

        color(new Color4f(r, g, b));

    }

    public static int getFontSize() {

        return RawDraw.fontSize();

    }

    public static void renderStringCenter(double x, double y, String str) {

        RawDraw.renderString(Maths.toInt(x - RawDraw.getFontLength(str) / 2), Maths.toInt(y), str);

    }

    public static void renderString(double x, double y, String str) {

        RawDraw.renderString(Maths.toInt(x), Maths.toInt(y + getFontSize() / 2), str);

    }

    public static void renderFontNumber(double x, double y, int width, int height, int number, NumberFont nfr) {

        nfr.tick(number);
        nfr.render(x, y, width, height);

    }

    public static void renderRect(Rectangle r) {

        renderRect(r.getOffsetX(), r.getOffsetY(), r.getWidth(), r.getHeight());

    }

    public static void renderFrame(Rectangle r, int size) {

        renderFrame(r.getOffsetX(), r.getOffsetY(), r.getX2(), r.getY2(), size);

    }

    public static void renderCircle(Circle c) {

        renderCircle(c.getX(), c.getY(), c.getRadius());

    }

    public static void renderRect2D(double x1, double y1, double x2, double y2) {

        renderRect(x1, y1, x2 - x1, y2 - y1);

    }

    public static void renderRect(double offsetX, double offsetY, double width, double height) {

        RawDraw.renderRect(Maths.toInt(offsetX), Maths.toInt(offsetY), Maths.toInt(width), Maths.toInt(height));

    }

    public static void renderFrame(double x1, double y1, double x2, double y2, int width) {

        for(int i = 0; i < width; i++) {
            RawDraw.renderFrame(Maths.toInt(x1) - i, Maths.toInt(y1) - i, Maths.toInt(x2 - x1) + i * 2, Maths.toInt(y2 - y1) + i * 2);
        }

    }

    public static void renderCircle(double x, double y, int r) {

        RawDraw.renderCircle(Maths.toInt(x - r), Maths.toInt(y - r), r * 2, r * 2);

    }

    public static void renderCircleSize(double x, double y, int s) {

        RawDraw.renderCircle(Maths.toInt(x - s / 2), Maths.toInt(y - s / 2), s, s);

    }

    public static void renderRing(double x, double y, int r, int width) {

        for(int i = 0; i < width; i++) {
            RawDraw.renderRing(Maths.toInt(x) - r - i, Maths.toInt(y) - r - i, r * 2 + i * 2, r * 2 + i * 2);
        }

    }

}
