package r2dx.stg.element;

import r2d.element.vector.ElementVector;
import r2d.render.desktop.Canvas;
import r2d.vector.Gravity;
import r2d.vector.Vector;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

public class ElementItem extends ElementVector {

    Texture textureAll;

    boolean hasGravity = true;

    public ElementItem(int type, int size, int width, int height, Texture texture) {

        super(width, height, 3, 90, texture);

        textureAll = texture;

        if(textureAll != null) {
            dye(type, size);
        }

    }

    public void tick(Canvas canvas) {

        if(hasGravity) {
            locate(Vector.vectorX(x, speed, angle), Gravity.gravityJump(y, timer, speed, 1));
        }
        else {
            super.tick(canvas);
        }

    }

    public void dye(int type, int size) {

        setTexture(TexSystem.cut(type * size, 0, size, size, textureAll));

    }

    public void setGravity(boolean gravity) {

        hasGravity = gravity;

    }

}
