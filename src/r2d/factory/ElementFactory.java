package r2d.factory;

import r2d.element.vector.ElementEffect;
import r2d.element.vector.ElementVector;
import rutil.container.Array;
import rwt.device.texture.Texture;

public class ElementFactory extends Factory {

    public static Array<ElementEffect> effectCache = new Array<>(max);
    public static Array<ElementVector> imageCache = new Array<>(min);

    static int lastEffect;
    static int lastImage;

    public static void init() {

        for(int i = 0; i < max; i++) {
            effectCache.add(new ElementEffect(0, 0, 0, 0, null));
        }

        for(int i = 0; i < min; i++) {
            imageCache.add(new ElementVector(0, 0, 0, 0, null));
        }

    }

    public static ElementEffect newEffect(double x, double y, int width, int height, double speed, double angle, Texture texture) {

        ElementEffect e = effectCache.get(lastEffect);

        e.locate(x, y);
        e.setSize(width, height);
        e.setTexture(texture);
        e.setSpeed(speed);
        e.setAngle(angle);

        e.reset();
        e.forRemove = false;

        lastEffect++;
        if(lastEffect >= effectCache.size()) {
            lastEffect = 0;
        }

        return e;

    }

    public static ElementEffect newEffect(ElementEffect cloner) {

        return newEffect(
                cloner.getX(),
                cloner.getY(),
                cloner.getWidth(),
                cloner.getHeight(),
                cloner.getSpeed(),
                cloner.getAngle(),
                cloner.getBackupTexture()
        );

    }

    public static ElementVector newVector(double x, double y, int width, int height, double speed, double angle, Texture texture) {

        ElementVector e = imageCache.get(lastImage);

        e.locate(x, y);
        e.setSize(width, height);
        e.setSpeed(speed);
        e.setAngle(angle);
        e.setTexture(texture);

        e.reset();

        lastImage++;
        if(lastImage >= imageCache.size()) {
            lastImage = 0;
        }

        return e;

    }

    public static ElementVector newVector(ElementVector cloner) {

        return newVector(
                cloner.getX(),
                cloner.getY(),
                cloner.getWidth(),
                cloner.getHeight(),
                cloner.getSpeed(),
                cloner.getAngle(),
                cloner.getBackupTexture()
                );

    }

}
