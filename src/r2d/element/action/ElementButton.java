package r2d.element.action;

import r2d.element.image.ElementImage;
import r2d.render.desktop.Canvas;
import rutil.maths.Maths;
import rutil.maths.ToolMath;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

public class ElementButton extends ElementImage {

    int maxWidth;
    int maxHeight;
    int minWidth;
    int minHeight;

    ButtonGroup buttonGroup;

    //effect increase speed
    public static int HANG_SPEED = 1;

    public ElementButton(double x, double y, int width, int height, Texture texture) {

        super(x, y, width, height, texture);

        maxWidth = Maths.toInt(width * 1.2);
        maxHeight = Maths.toInt(height * 1.2);
        minWidth = width;
        minHeight = height;

    }

    public void setGroupIn(ButtonGroup group) {

        buttonGroup = group;

    }

    public void tick(Canvas canvas) {

        if(isHanging()) {
            setSize(ToolMath.ofInt(width, HANG_SPEED, maxWidth), ToolMath.ofInt(height, HANG_SPEED, maxHeight));
            cacheTexture(getBackupTexture());
        }
        else {
            setSize(ToolMath.ofInt(width, -HANG_SPEED, minWidth), ToolMath.ofInt(height, -HANG_SPEED, minHeight));
            cacheTexture(TexSystem.trans(getBackupTexture(), 0.75));
        }

    }

    public boolean isHanging() {

        if(buttonGroup == null) {
            return super.isHanging();
        }

        return super.isHanging() && buttonGroup.isVisible();

    }

    public boolean isClicking() {

        if(buttonGroup == null) {
            return super.isClicking();
        }

        return super.isClicking() && buttonGroup.isVisible() && buttonGroup.isAvailable();

    }

}
