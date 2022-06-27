package r2d.render.manager;

import r2d.render.desktop.Canvas;
import rutil.container.Array;
import rutil.container.VarArray;
import rutil.maths.Maths;
import rutil.maths.ToolMath;
import rwt.device.graphic.Color4f;
import rwt.device.graphic.Draw;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

//Hard to test...

/**
 * Warn:
 * if you add bg in a short time,
 * it might not work well.
 * for example:
 * if(timer == 0) {
 *     invoke...;
 * }
 * else if(timer == 10) {
 *     invoke...;
 * }
 */
public class BackgroundManager extends FieldListener {

    public static final int MODE_LAYERS = 0;
    public static final int MODE_OP = 1;

    int mode;

    Array<Texture> buffer = new Array<>();
    Array<Texture> images = new Array<>();
    VarArray poses = new VarArray();
    VarArray speeds = new VarArray();
    VarArray speedsBuffer = new VarArray();
    Array<Texture> cut = new Array<>();

    double offset;

    ShadowManager shadow;

    public BackgroundManager(Canvas c) {

        super(c);

        shadow = new ShadowManager(canvas);

    }

    public void render() {

        if(images.size() == 0) {
            Draw.color(Color4f.BLACK);
            Draw.renderRect(field);
        }

        Texture texture;

        for(int i = 0; i < cut.size(); i++) {
            texture = cut.get(i);
            if(texture != null) {
                Draw.render(left, top, width, height, cut.get(i));
            }
        }

        shadow.render();

    }

    public void tick() {

        shadow.tick();

        if(shadow.inEnd()) {
            shadow.blackOut();

            images.clear();
            images.copyFrom(buffer);
            buffer.clear();

            speeds.clear();
            speeds.copyFrom(speedsBuffer);
            speedsBuffer.clear();

            for(int i = 0; i < images.size(); i++) {
                poses.add(0);
            }
        }//shadow arrives at max, copy into new array in dark

        cut.clear();
        Texture texture;

        for(int i = 0; i < images.size(); i++) {
            texture = images.get(i);
            if(texture != null) {
                int w = texture.getWidth();
                int cx = Maths.toInt((w - width) / 2 + offset);//cut on center of texture
                cut.add(TexSystem.cut(cx, Maths.toInt(poses.get(i)), width, height, texture));
            }

            switch(mode) {
                case MODE_LAYERS:
                    poses.set(i, ToolMath.ofDouble(poses.get(i), -speeds.get(i)));
                    if(poses.get(i) <= 0) {
                        poses.set(i, poses.get(i) + height);//return to height, and add the out value to be smooth.
                    }
                    break;
                case MODE_OP:
                    poses.set(i, ToolMath.ofDouble(poses.get(i), speeds.get(i), height));
                    if(poses.get(i) >= height) {
                        poses.set(i, poses.get(i) - height);
                    }
                    break;
            }
        }

    }

    //双层下滚动背景
    public void layersBackground(Texture textureLow, Texture textureHigh, double speedLow, double speedHigh) {

        updateImage();

        if(speedLow > 0) {
            buffer.add(textureLow);
            speedsBuffer.add(speedLow);
        }
        buffer.add(textureLow);
        speedsBuffer.add(speedLow);

        if(speedHigh > 0) {
            buffer.add(textureHigh);
            speedsBuffer.add(speedHigh);
        }
        buffer.add(textureHigh);
        speedsBuffer.add(speedHigh);

        mode = MODE_LAYERS;

    }

    //双层上滚动背景
    public void layersOppositeBackground(Texture textureLow, Texture textureHigh, double speedLow, double speedHigh) {

        updateImage();

        if(speedLow > 0) {
            buffer.add(textureLow);
            speedsBuffer.add(speedLow);
        }
        buffer.add(textureLow);
        speedsBuffer.add(speedLow);

        if(speedHigh > 0) {
            buffer.add(textureHigh);
            speedsBuffer.add(speedHigh);
        }
        buffer.add(textureHigh);
        speedsBuffer.add(speedHigh);

        mode = MODE_OP;

    }

    //静态背景
    public void staticBackground(Texture texture) {

        updateImage();

        buffer.add(texture);

        mode = -1;

    }

    //静态双层背景
    public void staticBackground(Texture textureLow, Texture textureHigh) {

        updateImage();

        buffer.add(textureLow);
        buffer.add(textureHigh);

        mode = -1;

    }

    public void tranX(double mx) {

        offset += mx;

    }

    public void tranSpeed(double ms, double limit) {

        for(int i = 0; i < images.size(); i++) {
            speeds.set(i, ToolMath.ofDouble(speeds.get(i), ms, limit));
        }

    }

    private void updateImage() {

        shadow.blackIn();

    }

}
