package r2d.element.action;

import r2d.render.desktop.Canvas;
import rutil.maths.ToolMath;
import rutil.shape.Rectangle;
import rwt.device.graphic.Color4f;
import rwt.device.graphic.Draw;
import rwt.device.graphic.TextFont;

public class ElementButtonText extends ElementButton {

    public static double HANG_SPEED = 0.03;

    String text;
    String info;

    double light;
    double infoTran;

    TextFont font = TextFont.MICROSOFT;
    Color4f color = Color4f.WHITE;

    public ElementButtonText(double x, double y, int width, int height, String text) {

        this(x, y, width, height, text, "");

    }

    public ElementButtonText(double x, double y, int width, int height, String text, String info) {

        super(x, y, width, height, null);

        this.text = text;
        this.info = info;

    }

    public void render() {

        Rectangle r = getRect();

        Draw.color(Color4f.SHADOW);
        Draw.renderRect(r);

        Draw.color(Color4f.WHITE.retrans(light));
        Draw.renderFrame(r, 1);

        Draw.font(font);
        Draw.color(color);
        Draw.renderStringCenter(x, y, text);

        Draw.color(color.retrans(infoTran));
        Draw.renderStringCenter(x, getY2(), info);

    }

    public void tick(Canvas canvas) {

        if(isHanging()) {
            light = ToolMath.ofDouble(light, HANG_SPEED, 0.5);
            infoTran = ToolMath.ofDouble(infoTran, HANG_SPEED, 1);
        }
        else {
            light = ToolMath.ofDouble(light, -HANG_SPEED);
            infoTran = ToolMath.ofDouble(infoTran, -HANG_SPEED);
        }

    }

    public void setStyle(TextFont f, Color4f c) {

        font = f;
        color = c;

    }

}
