package r2d.render.manager;

import r2d.render.desktop.Canvas;
import rutil.shape.Rectangle;

public class FieldListener {

    public Rectangle field;
    public Canvas canvas;

    public int left;
    public int top;
    public int right;
    public int bottom;
    public int width;
    public int height;
    public int cx;
    public int cy;

    public FieldListener(Canvas c) {

        canvas = c;
        field = canvas.field;

    }

    //must hand invoke -- don't forget!
    public void updateField() {

        field = canvas.field;

        width = field.getWidth();
        height = field.getHeight();
        left = field.getOffsetX();
        top = field.getOffsetY();
        right = field.getX2();
        bottom = field.getY2();
        cx = field.getX();
        cy = field.getY();

    }

}
