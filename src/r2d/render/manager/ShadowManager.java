package r2d.render.manager;

import r2d.render.desktop.Canvas;
import rutil.maths.ToolMath;
import rutil.shape.Rectangle;
import rwt.device.graphic.Draw;

public class ShadowManager {

    public static double defaultSpeed = 0.025;

    double maxBlack = 1;

    boolean blackIn;
    boolean blackOut;
    double black;

    Canvas canvas;
    Rectangle field;

    public ShadowManager(Canvas c) {

        canvas = c;

    }

    public void setRenderField(Rectangle field) {

        this.field = field;

    }

    public void setMaxBlack(double max) {

        maxBlack = max;

    }

    public void blackIn() {

        if(!blackIn) {
            blackIn = true;
            blackOut = false;
            black = 0;
        }

    }

    public void blackOut() {

        if(!blackOut) {
            blackIn = false;
            blackOut = true;
            black = maxBlack;
        }

    }

    public void tick() {

        if(blackOut || blackIn) {
            if(blackOut) {
                black = ToolMath.ofDouble(black, -defaultSpeed);
            }
            else {
                black = ToolMath.ofDouble(black, defaultSpeed, maxBlack);
            }
        }

    }

    public void render() {

        Draw.color4f(0, 0, 0, black);

        if(field != null) {
            Draw.renderRect(field);
        }
        else {
            Draw.renderRect(0, 0, canvas.bufferWidth, canvas.bufferHeight);
        }

    }

    public boolean inEnd() {

        return blackIn && black == maxBlack;

    }

    public boolean outEnd() {

        return blackOut && black == 0;

    }

    public boolean shadowing() {

        return black > 0 && black < maxBlack;

    }

    public void clear() {

        blackIn = blackOut = false;
        black = 0;

    }

}
