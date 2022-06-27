package r2d.render.manager;

import r2d.element.vector.ElementEffectShape;
import r2d.logic.WaitTimer;
import r2d.render.desktop.Canvas;
import rutil.container.Array;
import rwt.device.graphic.Color4f;
import rwt.device.input.Input;
import rwt.device.input.Keys;
import rwt.device.graphic.Draw;

public class MouseManager {

    public static int SIZE_UP = 2;
    public static double TRANS_DOWN = -0.04;
    public static double TRANS_START = 0.5;
    public static int SIZE_START = 5;
    public static int COOL_DOWN = 15;

    Canvas canvas;

    double mouseTran;
    int mouseX;
    int mouseY;

    WaitTimer waiter = new WaitTimer();
    Array<ElementEffectShape> effects = new Array<>();

    public MouseManager(Canvas c) {

        canvas = c;

    }

    public void renderMouse() {

        canvas.defaultRender(effects);

        Draw.color(Color4f.WHITE.retrans(mouseTran));
        Draw.renderCircle(mouseX, mouseY, 3);

    }

    public void checkMouse() {

        waiter.update();

        canvas.vectorTick(effects);
        mouseTran = canvas.cycle * 0.6 + 0.4;

        mouseX = Input.getMouseX();
        mouseY = Input.getMouseY();

        if(Input.isMouseClicking(Keys.M_LEFT) && waiter.isWaitBack()) {
            ElementEffectShape effect = new ElementEffectShape(SIZE_START, SIZE_START, 0, 0);
            effect.setTransparency(TRANS_START);
            effect.locate(mouseX, mouseY);
            effect.setSizeCrease(SIZE_UP);
            effect.setTranCrease(TRANS_DOWN);
            effect.setNotCheckOut(true);
            effects.add(effect);
            waiter.wait(COOL_DOWN);
        }

    }

}
