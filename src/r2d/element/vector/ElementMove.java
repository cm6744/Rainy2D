package r2d.element.vector;

import r2d.render.desktop.Canvas;
import r2d.render.image.AnimatedImage;
import r2d.vector.Vector;
import rutil.maths.Maths;
import rwt.device.texture.Texture;

public class ElementMove extends ElementVector {

    public AnimatedImage frontImages = new AnimatedImage(5);
    public AnimatedImage sideImages = new AnimatedImage(5);

    public ElementMove(int width, int height, double speed, double angle, Texture texture) {

        super(width, height, speed, angle, texture);

    }

    public void tick(Canvas canvas) {

        super.tick(canvas);

        animate();

    }

    private boolean animateInit;

    public void animate() {

        if(!animateInit) {
            frontImages.loadFromImage(4, 2, 0, texture);
            sideImages.loadFromImage(4, 2, 1, texture);
            animateInit = true;
        }

        frontImages.tick();
        sideImages.tick();

        double xa = Vector.vectorX(x, speed, angle);

        if(Maths.similarCompare(xa, x, 1) || speed == 0) {
            texture = frontImages.getLeftTextureNow();
        }
        else if(xa > x) {
            texture = sideImages.getRightTextureNow();
        }
        else if(xa < x) {
            texture = sideImages.getLeftTextureNow();
        }

    }

}
