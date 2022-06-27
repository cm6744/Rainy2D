package rwt.device.texture;

import rutil.maths.Maths;
import rwt.device.graphic.Color4f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class TexSystem {

    protected static BufferedImage createModule(Texture texture) {

        return createModule(texture.getWidth(), texture.getHeight());

    }

    protected static BufferedImage createModule(int w, int h) {

        return new BufferedImage(w, h, Transparency.TRANSLUCENT);

    }

    public static Texture cut(int x, int y, int width, int height, Texture texture) {

        if(texture == null) return null;

        //check the field isn't out of raster
        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(width <= 0) width = 1;
        if(height <= 0) height = 1;

        if(x + width >= texture.getWidth()) {
            width = texture.getWidth() - x;
        }
        if(y + height >= texture.getHeight()) {
            height = texture.getHeight() - y;
        }

        return new Texture(texture.data.getSubimage(x, y, width, height));

    }

    public static Texture cutP(double wp, double hp, Texture texture) {

        return cut(0, 0, Maths.toInt(wp * texture.getWidth()), Maths.toInt(hp * texture.getHeight()), texture);

    }

    public static Texture cutType(int cutSize, int line, int row, Texture texture) {

        return cut(row * cutSize, line * cutSize, cutSize, cutSize, texture);

    }

    public static Texture cutRow(int cut, int index, Texture texture) {

        return cutTile(1, cut, 0, index, texture);

    }

    public static Texture cutLine(int cut, int index, Texture texture) {

        return cutTile(cut, 1, index, 0, texture);

    }

    /**
     * @param wc x-cut-num
     * @param hc y-cut-num
     * @param wi x-index
     * @param hi y-index
     */
    public static Texture cutTile(int wc, int hc, int wi, int hi, Texture texture) {

        if(texture == null) return null;

        int eachWidth = texture.getWidth() / wc;
        int eachHeight = texture.getHeight() / hc;

        return cut(eachWidth * wi, eachHeight * hi, eachWidth, eachHeight, texture);

    }

    public static Texture rotate(Texture texture, double angle) {

        if(texture == null) return null;

        int width = texture.getWidth();
        int height = texture.getHeight();

        BufferedImage out = createModule(texture);
        Graphics2D g = (Graphics2D) out.getGraphics();

        g.rotate(Maths.toRadians(angle), width / 2, height / 2);
        g.drawImage(texture.source(), 0, 0, width, height, null);
        g.dispose();

        return new Texture(out);

    }

    public static Texture trans(Texture texture, double trans) {

        if(texture == null) return null;

        trans = Color4f.checkIn(trans);

        BufferedImage out = createModule(texture);
        Graphics2D g = (Graphics2D) out.getGraphics();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) trans);
        g.setComposite(ac);

        g.drawImage(texture.source(), 0, 0, texture.getWidth(), texture.getHeight(), null);
        g.dispose();

        return new Texture(out);

    }

    /**
     * Not work at Java 8
     */
    public static Texture light(Texture texture, double lights) {

        if(texture == null) return null;

        lights = Color4f.checkIn(lights);

        BufferedImage out = createModule(texture);

        RescaleOp ro = new RescaleOp((float) lights, 0, null);
        ro.filter(texture.source(), out);

        return new Texture(out);

    }

    public static Texture mirror(Texture texture) {

        if(texture == null) return null;

        int width = texture.getWidth();
        int height = texture.getHeight();

        BufferedImage out = createModule(texture);

        Graphics2D g = (Graphics2D) out.getGraphics();

        g.drawImage(texture.source(), 0, 0, width, height, width, 0, 0, height, null);
        g.dispose();

        return new Texture(out);

    }

}
